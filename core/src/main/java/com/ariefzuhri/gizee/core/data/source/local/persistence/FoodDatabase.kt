package com.ariefzuhri.gizee.core.data.source.local.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ariefzuhri.gizee.core.data.source.local.entity.*

@Database(
    entities = [
        FoodEntity::class,
        HistoryEntity::class,
        NutrientEntity::class,
        HistoryFoodCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
}