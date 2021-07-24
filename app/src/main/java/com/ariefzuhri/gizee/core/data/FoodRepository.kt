package com.ariefzuhri.gizee.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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
    private var result = listOf<FoodEntity>()
    override fun searchFoodsByNaturalLanguage(query: String): LiveData<Resource<List<Food>>> =
        object : NetworkBoundResource<List<Food>, FoodResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Food>> {
                val output = MutableLiveData<List<FoodEntity>>()
                output.value = result

                return Transformations.map(output) {
                    FoodMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Food>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<FoodResponse>> {
                return remoteDataSource.searchFoodsByNaturalLanguage(query)
            }

            override fun saveCallResult(data: FoodResponse) {
                val foods = FoodMapper.mapResponseToEntities(data)
                result = foods
            }
        }.asLiveData()

    override fun getFavorites(): LiveData<List<Food>> =
        Transformations.map(localDataSource.getFoods()) {
            FoodMapper.mapEntitiesToDomain(it)
        }

    override fun isFavorite(id: String): LiveData<Food> =
        Transformations.map(localDataSource.getFood(id)) {
            FoodMapper.mapEntityToDomain(it)
        }

    override fun getHistory(): LiveData<List<History>> =
        Transformations.map(localDataSource.getHistory()) {
            HistoryMapper.mapEntitiesToDomain(it)
        }

    override fun getNutrients(): LiveData<Resource<List<Nutrient>>> =
        object : NetworkBoundResource<List<Nutrient>, List<NutrientResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Nutrient>> {
                return Transformations.map(localDataSource.getNutrients()) {
                    NutrientMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Nutrient>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<NutrientResponse>>> {
                return remoteDataSource.getNutrients()
            }

            override fun saveCallResult(data: List<NutrientResponse>) {
                val nutrients = NutrientMapper.mapResponsesToEntities(data)
                localDataSource.insertNutrients(nutrients)
            }
        }.asLiveData()

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