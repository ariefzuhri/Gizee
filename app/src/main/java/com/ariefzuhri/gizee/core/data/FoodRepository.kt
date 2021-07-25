package com.ariefzuhri.gizee.core.data

import com.ariefzuhri.gizee.core.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.domain.repository.IFoodRepository
import com.ariefzuhri.gizee.core.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FoodRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFoodRepository {

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): FoodRepository =
            instance ?: synchronized(this) {
                instance ?: FoodRepository(remoteData, localData, appExecutors)
            }
    }

    // Next time, I will make it to store fetch results to database
    override fun getFoodsByNaturalLanguage(query: String): Flow<Resource<List<Food>>> =
        object : NetworkBoundResource<List<Food>, FoodResponse>() {
            var result = listOf<FoodEntity>()

            override fun loadFromDB(): Flow<List<Food>> {
                val data = flowOf(result)
                return data.map { FoodMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Food>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<FoodResponse>> {
                return remoteDataSource.getFoodsByNaturalLanguage(query)
            }

            override suspend fun saveCallResult(data: FoodResponse) {
                val foods = FoodMapper.mapResponseToEntities(data)
                result = foods
            }
        }.asFlow()

    override fun getFavorites(): Flow<List<Food>> =
        localDataSource.getFoods().map {
            FoodMapper.mapEntitiesToDomain(it)
        }

    override fun isFavorite(id: String): Flow<Boolean> =
        localDataSource.getFood(id).map {
            @Suppress("SENSELESS_COMPARISON")
            it != null
        }

    override fun getHistory(): Flow<List<History>> =
        localDataSource.getHistory().map {
            HistoryMapper.mapEntitiesToDomain(it)
        }

    override fun getNutrients(): Flow<Resource<List<Nutrient>>> =
        object : NetworkBoundResource<List<Nutrient>, List<NutrientResponse>>() {
            override fun loadFromDB(): Flow<List<Nutrient>> {
                return localDataSource.getNutrients().map {
                    NutrientMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Nutrient>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<NutrientResponse>>> {
                return remoteDataSource.getNutrients()
            }

            override suspend fun saveCallResult(data: List<NutrientResponse>) {
                val nutrients = NutrientMapper.mapResponsesToEntities(data)
                localDataSource.insertNutrients(nutrients)
            }
        }.asFlow()

    override fun insertFavorite(food: Food) {
        val foodEntity = FoodMapper.mapDomainToEntity(food)
        appExecutors.diskIO().execute { localDataSource.insertFavorite(foodEntity) }
    }

    override fun insertHistory(history: History) {
        val historyEntity = HistoryMapper.mapDomainToEntity(history)
        appExecutors.diskIO().execute { localDataSource.insertHistory(historyEntity) }
    }

    override fun deleteFavorite(food: Food) {
        val foodEntity = FoodMapper.mapDomainToEntity(food)
        appExecutors.diskIO().execute { localDataSource.deleteFavorite(foodEntity) }
    }

    override fun deleteHistory(history: History) {
        val historyEntity = HistoryMapper.mapDomainToEntity(history)
        appExecutors.diskIO().execute { localDataSource.deleteHistory(historyEntity) }
    }

    override fun clearHistory() {
        appExecutors.diskIO().execute { localDataSource.clearHistory() }
    }
}