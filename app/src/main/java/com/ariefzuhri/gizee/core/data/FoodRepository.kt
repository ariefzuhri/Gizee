package com.ariefzuhri.gizee.core.data

import androidx.lifecycle.LiveData
import com.ariefzuhri.gizee.core.data.source.local.LocalDataSource
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.data.source.remote.RemoteDataSource
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import com.ariefzuhri.gizee.core.utils.AppExecutors
import com.ariefzuhri.gizee.core.utils.DataMapper

class FoodRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) {

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

    fun searchFoodsByNaturalLanguage(query: String): LiveData<ApiResponse<FoodResponse>> =
        remoteDataSource.searchFoodsByNaturalLanguage(query)

    fun getFavorites(): LiveData<List<FoodEntity>> =
        localDataSource.getFoods()

    fun isFavorite(id: String): LiveData<FoodEntity> =
        localDataSource.getFood(id)

    fun getHistory(): LiveData<List<HistoryEntity>> =
        localDataSource.getHistory()

    fun getNutrients(): LiveData<Resource<List<NutrientEntity>>> =
        object : NetworkBoundResource<List<NutrientEntity>, List<NutrientResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<NutrientEntity>> {
                return localDataSource.getNutrients()
            }

            override fun shouldFetch(data: List<NutrientEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<NutrientResponse>>> {
                return remoteDataSource.getNutrients()
            }

            override fun saveCallResult(data: List<NutrientResponse>) {
                val nutrients = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertNutrients(nutrients)
            }
        }.asLiveData()

    fun insertFavorite(food: FoodEntity) =
        appExecutors.diskIO().execute { localDataSource.insertFavorite(food) }

    fun insertHistory(history: HistoryEntity) =
        appExecutors.diskIO().execute { localDataSource.insertHistory(history) }

    fun deleteFavorite(food: FoodEntity) =
        appExecutors.diskIO().execute { localDataSource.deleteFavorite(food) }

    fun deleteHistory(history: HistoryEntity) =
        appExecutors.diskIO().execute { localDataSource.deleteHistory(history) }

    fun clearHistory() =
        appExecutors.diskIO().execute { localDataSource.clearHistory() }
}