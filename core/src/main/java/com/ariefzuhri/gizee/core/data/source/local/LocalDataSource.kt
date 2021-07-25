package com.ariefzuhri.gizee.core.data.source.local

import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.data.source.local.room.FoodDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val foodDao: FoodDao) {

    fun getFoods(): Flow<List<FoodEntity>> = foodDao.getFoods()

    fun getFood(id: String): Flow<FoodEntity> = foodDao.getFood(id)

    fun getHistory(): Flow<List<HistoryEntity>> = foodDao.getHistory()

    fun getNutrients(): Flow<List<NutrientEntity>> = foodDao.getNutrients()

    fun insertFavorite(food: FoodEntity) = foodDao.insertFood(food)

    fun insertHistory(history: HistoryEntity) = foodDao.insertHistory(history)

    suspend fun insertNutrients(nutrients: List<NutrientEntity>) =
        foodDao.insertNutrients(nutrients)

    fun deleteFavorite(food: FoodEntity) = foodDao.deleteFood(food)

    fun deleteHistory(history: HistoryEntity) = foodDao.deleteHistory(history)

    fun clearHistory() = foodDao.deleteHistory()
}