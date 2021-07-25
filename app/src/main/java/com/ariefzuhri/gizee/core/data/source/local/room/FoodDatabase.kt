package com.ariefzuhri.gizee.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.utils.Converters

@Database(
    entities = [FoodEntity::class, HistoryEntity::class, NutrientEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
}