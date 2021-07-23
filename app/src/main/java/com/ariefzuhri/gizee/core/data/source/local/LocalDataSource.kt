package com.ariefzuhri.gizee.core.data.source.local

import androidx.lifecycle.LiveData
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.data.source.local.room.FoodDao

class LocalDataSource private constructor(private val foodDao: FoodDao) {

    companion object {
        private val instance: LocalDataSource? = null

        fun getInstance(foodDao: FoodDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(foodDao)
            }
    }

    fun getFoods(): LiveData<List<FoodEntity>> = foodDao.getFoods()

    fun getFood(id: String): LiveData<FoodEntity> = foodDao.getFood(id)

    fun getHistory(): LiveData<List<HistoryEntity>> = foodDao.getHistory()

    fun getNutrients(): LiveData<List<NutrientEntity>> = foodDao.getNutrients()

    fun insertFavorite(food: FoodEntity) = foodDao.insertFood(food)

    fun insertHistory(history: HistoryEntity) = foodDao.insertHistory(history)

    fun insertNutrients(nutrients: List<NutrientEntity>) = foodDao.insertNutrients(nutrients)

    fun deleteFavorite(food: FoodEntity) = foodDao.deleteFood(food)

    fun deleteHistory(history: HistoryEntity) = foodDao.deleteHistory(history)

    fun clearHistory() = foodDao.deleteHistory()
}