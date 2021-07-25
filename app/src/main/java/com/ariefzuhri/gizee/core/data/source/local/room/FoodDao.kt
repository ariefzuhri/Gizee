package com.ariefzuhri.gizee.core.data.source.local.room

import androidx.room.*
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods ORDER BY name ASC")
    fun getFoods(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE id =:id")
    fun getFood(id: String): Flow<FoodEntity>

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM nutrients ORDER BY name DESC")
    fun getNutrients(): Flow<List<NutrientEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(food: FoodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrients(nutrients: List<NutrientEntity>)

    @Delete
    fun deleteFood(food: FoodEntity)

    @Query("DELETE FROM history")
    fun deleteHistory()

    @Delete
    fun deleteHistory(history: HistoryEntity)
}