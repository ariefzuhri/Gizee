package com.ariefzuhri.gizee.core.data.source.local

import com.ariefzuhri.gizee.core.data.source.local.entity.*
import com.ariefzuhri.gizee.core.data.source.local.persistence.FoodDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val foodDao: FoodDao) {

    fun getHistoryWithFoods(query: String): Flow<HistoryWithFoods> =
        foodDao.getHistoryWithFoods(query)

    fun getNutrients(): Flow<List<NutrientEntity>> =
        foodDao.getNutrients()

    fun getHistoryWithFoods(): Flow<List<HistoryEntity>> =
        foodDao.getHistory()

    fun getFavoriteFoods(): Flow<List<FoodEntity>> =
        foodDao.getFavoriteFoods()

    suspend fun insertFoods(query: String, foodEntities: List<FoodEntity>) {
        foodDao.insertFoods(query, foodEntities)
    }

    suspend fun insertHistory(historyEntity: HistoryEntity) =
        foodDao.insertHistory(historyEntity)

    suspend fun insertNutrients(nutrientEntities: List<NutrientEntity>) =
        foodDao.insertNutrients(nutrientEntities)

    fun updateFavoriteFood(foodId: String, newState: Boolean) =
        foodDao.updateFavoriteFood(foodId, newState)

    fun deleteHistory() =
        foodDao.deleteHistory()

    fun deleteHistory(historyEntity: HistoryEntity) =
        foodDao.deleteHistory(historyEntity)
}