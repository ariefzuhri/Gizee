package com.ariefzuhri.gizee.core.data.repository

import com.ariefzuhri.gizee.core.data.repository.datamapper.*
import com.ariefzuhri.gizee.core.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
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

class FoodRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFoodRepository {

    override fun getSearchResult(query: String): Flow<Resource<History>> =
        object : NetworkBoundResource<History, FoodResponse>() {
            var historyEntity = HistoryEntity(query)
            var result = listOf<FoodEntity>()

            override fun loadFromDB(): Flow<History> {
                val data = flowOf(result)
                return data.map { mapEntityToDomain(historyEntity, it) }
            }

            override fun shouldFetch(data: History?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<FoodResponse>> {
                localDataSource.insertHistory(historyEntity)
                return remoteDataSource.getFoodsByNaturalLanguage(query)
            }

            override suspend fun saveCallResult(data: FoodResponse) {
                val foodEntities = mapResponseToEntities(data)
                result = foodEntities
            }
        }.asFlow()

    override fun getNutrients(): Flow<Resource<List<Nutrient>>> =
        object : NetworkBoundResource<List<Nutrient>, List<NutrientResponse>>() {
            override fun loadFromDB(): Flow<List<Nutrient>> {
                return localDataSource.getNutrients().map {
                    mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Nutrient>?): Boolean {
                return data.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<NutrientResponse>>> {
                return remoteDataSource.getNutrients()
            }

            override suspend fun saveCallResult(data: List<NutrientResponse>) {
                val nutrientEntities = mapResponsesToEntities(data)
                localDataSource.insertNutrients(nutrientEntities)
            }
        }.asFlow()

    override fun getHistory(): Flow<List<History>> =
        localDataSource.getHistoryWithFoods().map {
            mapEntitiesToDomain(it)
        }

    override fun getFavorites(): Flow<List<Food>> =
        localDataSource.getFavoriteFoods().map {
            mapEntitiesToDomain(it)
        }

    override fun isFavorite(foodId: String): Flow<Boolean> =
        localDataSource.getFavoriteFood(foodId).map {
            @Suppress("SENSELESS_COMPARISON")
            it != null
        }

    override fun setFavorite(food: Food, newState: Boolean) {
        val foodEntity = mapDomainToEntity(food)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteFood(foodEntity, newState)
        }
    }

    override fun clearHistory() {
        appExecutors.diskIO().execute {
            localDataSource.deleteHistory()
        }
    }

    override fun deleteHistory(history: History) {
        val historyEntity = mapDomainToEntity(history)
        appExecutors.diskIO().execute {
            localDataSource.deleteHistory(historyEntity)
        }
    }
}