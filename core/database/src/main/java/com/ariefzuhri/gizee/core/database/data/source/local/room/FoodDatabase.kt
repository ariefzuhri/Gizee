package com.ariefzuhri.gizee.core.database.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ariefzuhri.gizee.core.database.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.NutrientEntity

const val DATABASE_NAME_ROOM = "food.db"

@Database(
    entities = [
        FoodEntity::class,
        HistoryEntity::class,
        NutrientEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
}