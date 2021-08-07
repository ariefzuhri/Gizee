package com.ariefzuhri.gizee.core.data.source.local

import com.ariefzuhri.gizee.core.data.source.local.entity.*
import com.ariefzuhri.gizee.core.data.source.local.persistence.FoodDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val foodDao: FoodDao) {

    fun getNutrients(): Flow<List<NutrientEntity>> =
        foodDao.getNutrients()

    fun getHistoryWithFoods(): Flow<List<HistoryEntity>> =
        foodDao.getHistory()

    fun getFavoriteFoods(): Flow<List<FoodEntity>> =
        foodDao.getFavoriteFoods()

    fun getFavoriteFood(id: String) =
        foodDao.getFavoriteFood(id)

    suspend fun insertHistory(historyEntity: HistoryEntity) =
        foodDao.insertHistory(historyEntity)

    suspend fun insertNutrients(nutrientEntities: List<NutrientEntity>) =
        foodDao.insertNutrients(nutrientEntities)

    fun setFavoriteFood(foodEntity: FoodEntity, newState: Boolean) {
        if (newState) foodDao.insertFood(foodEntity)
        else foodDao.deleteFood(foodEntity)
    }

    fun deleteHistory() =
        foodDao.deleteHistory()

    fun deleteHistory(historyEntity: HistoryEntity) =
        foodDao.deleteHistory(historyEntity)
}