package com.ariefzuhri.gizee.core.database.data.source.local.persistence

import androidx.room.*
import com.ariefzuhri.gizee.core.database.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.NutrientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * FROM nutrients ORDER BY name DESC")
    fun getNutrients(): Flow<List<NutrientEntity>>

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM foods ORDER BY name ASC")
    fun getFavoriteFoods(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM foods WHERE id = :id")
    fun getFavoriteFood(id: String): Flow<FoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrients(nutrientEntities: List<NutrientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(foodEntity: FoodEntity)

    @Query("DELETE FROM history")
    fun deleteHistory()

    @Delete
    fun deleteHistory(historyEntity: HistoryEntity)

    @Delete
    fun deleteFood(foodEntity: FoodEntity)
}