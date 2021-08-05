package com.ariefzuhri.gizee.core.data.source.local.persistence

import androidx.room.*
import com.ariefzuhri.gizee.core.data.source.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class FoodDao {

    @Transaction
    @Query("SELECT * FROM history WHERE queries = :query")
    abstract fun getHistoryWithFoods(query: String): Flow<HistoryWithFoods>

    @Query("SELECT * FROM nutrients ORDER BY name DESC")
    abstract fun getNutrients(): Flow<List<NutrientEntity>>

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    abstract fun getHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM foods WHERE isFavorite = 1 ORDER BY name ASC")
    abstract fun getFavoriteFoods(): Flow<List<FoodEntity>>

    @Transaction
    open suspend fun insertFoods(query: String, foodEntities: List<FoodEntity>) {
        insertBulkFoods(foodEntities) // Insert food first
        val historyFoodCrossRefs = foodEntities.map {
            HistoryFoodCrossRef(query, it.id)
        }
        insertHistoryFoodCrossRefs(historyFoodCrossRefs)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHistory(historyEntity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNutrients(nutrientEntities: List<NutrientEntity>)

    @Transaction
    open fun updateFavoriteFood(foodId: String, newState: Boolean) {
        updateFavoriteFoodById(foodId, newState)
        if (!newState) deleteRedundantFoods()
    }

    @Transaction
    open fun deleteHistory() {
        deleteAllHistory()
        deleteRedundantFoods()
    }

    @Transaction
    open fun deleteHistory(historyEntity: HistoryEntity) {
        deleteSpecificHistory(historyEntity)
        deleteRedundantFoods()
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertHistoryFoodCrossRefs(historyFoodCrossRefs: List<HistoryFoodCrossRef>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertBulkFoods(foodEntities: List<FoodEntity>)

    @Query("UPDATE foods SET isFavorite = :newState WHERE id = :foodId")
    protected abstract fun updateFavoriteFoodById(foodId: String, newState: Boolean)

    @Query("DELETE FROM history")
    protected abstract fun deleteAllHistory()

    @Delete
    protected abstract fun deleteSpecificHistory(historyEntity: HistoryEntity)

    @Query("DELETE FROM foods WHERE isFavorite = 0 AND id NOT IN (SELECT foodId FROM historyFoodCrossRefs WHERE foodId = id LIMIT 1)")
    protected abstract fun deleteRedundantFoods()
}