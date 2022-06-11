package com.ariefzuhri.gizee.core.database.data.source.local

import com.ariefzuhri.gizee.core.database.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.database.data.source.local.room.FoodDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val foodDao: FoodDao) {

    fun getNutrients(): Flow<List<NutrientEntity>> {
        return foodDao.getNutrients()
    }

    fun getHistoryWithFoods(): Flow<List<HistoryEntity>> {
        return foodDao.getHistory()
    }

    fun getFavoriteFoods(): Flow<List<FoodEntity>> {
        return foodDao.getFavoriteFoods()
    }

    fun getFavoriteFood(id: String): Flow<FoodEntity> {
        return foodDao.getFavoriteFood(id)
    }

    suspend fun insertHistory(historyEntity: HistoryEntity) {
        foodDao.insertHistory(historyEntity)
    }

    suspend fun insertNutrients(nutrientEntities: List<NutrientEntity>) {
        foodDao.insertNutrients(nutrientEntities)
    }

    fun setFavoriteFood(foodEntity: FoodEntity, newState: Boolean) {
        if (newState) foodDao.insertFood(foodEntity)
        else foodDao.deleteFood(foodEntity)
    }

    fun deleteHistory() {
        foodDao.deleteHistory()
    }

    fun deleteHistory(historyEntity: HistoryEntity) {
        foodDao.deleteHistory(historyEntity)
    }
}