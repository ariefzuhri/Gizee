package com.ariefzuhri.gizee.core.data

import com.ariefzuhri.gizee.core.data.source.local.LocalDataSource
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
import kotlinx.coroutines.flow.map

class FoodRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IFoodRepository {

    override fun searchFoods(query: String): Flow<Resource<History>> =
        object : NetworkBoundResource<History, FoodResponse>() {
            override fun loadFromDB(): Flow<History> {
                return localDataSource.getHistoryWithFoods(query).map {
                    @Suppress("SENSELESS_COMPARISON")
                    if (it == null) History("", 0, null)
                    else HistoryMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: History?): Boolean {
                return data!!.foods.isNullOrEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<FoodResponse>> {
                localDataSource.insertHistory(HistoryEntity(query))
                return remoteDataSource.getFoodsByNaturalLanguage(query)
            }

            override suspend fun saveCallResult(data: FoodResponse) {
                val foodEntities = FoodMapper.mapResponseToEntities(data)
                localDataSource.insertFoods(query, foodEntities)
            }
        }.asFlow()


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
                val nutrientEntities = NutrientMapper.mapResponsesToEntities(data)
                localDataSource.insertNutrients(nutrientEntities)
            }
        }.asFlow()

    override fun getHistory(): Flow<List<History>> =
        localDataSource.getHistoryWithFoods().map {
            HistoryMapper.mapEntitiesToDomain(it)
        }

    override fun getFavorites(): Flow<List<Food>> =
        localDataSource.getFavoriteFoods().map {
            FoodMapper.mapEntitiesToDomain(it)
        }

    override fun updateFavorite(foodId: String, newState: Boolean) {
        appExecutors.diskIO().execute { localDataSource.updateFavoriteFood(foodId, newState) }
    }

    override fun clearHistory() {
        appExecutors.diskIO().execute { localDataSource.deleteHistory() }
    }

    override fun deleteHistory(history: History) {
        val historyEntity = HistoryMapper.mapDomainToEntity(history)
        appExecutors.diskIO().execute { localDataSource.deleteHistory(historyEntity) }
    }
}