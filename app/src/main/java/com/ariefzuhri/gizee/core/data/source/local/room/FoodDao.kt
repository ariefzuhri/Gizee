package com.ariefzuhri.gizee.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods ORDER BY name ASC")
    fun getFoods(): LiveData<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE id =:id")
    fun getFood(id: String): LiveData<FoodEntity>

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun getHistory(): LiveData<List<HistoryEntity>>

    @Query("SELECT * FROM nutrients ORDER BY name DESC")
    fun getNutrients(): LiveData<List<NutrientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food: FoodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNutrients(nutrients: List<NutrientEntity>)

    @Delete
    fun deleteFood(food: FoodEntity)

    @Query("DELETE FROM history")
    fun deleteHistory()

    @Delete
    fun deleteHistory(history: HistoryEntity)
}