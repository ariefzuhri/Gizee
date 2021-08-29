package com.ariefzuhri.gizee.core.data.source.local.persistence

import androidx.room.TypeConverter
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {

    private val moshi = Moshi.Builder().build()

    private val nutrientsType = Types.newParameterizedType(
        List::class.java, NutrientEntity::class.java
    )
    private val nutrientsAdapter = moshi.adapter<List<NutrientEntity>>(nutrientsType)

    @TypeConverter
    fun nutrientsToString(value: List<NutrientEntity>): String {
        return nutrientsAdapter.toJson(value)
    }

    @TypeConverter
    fun stringToNutrients(value: String): List<NutrientEntity>? {
        return nutrientsAdapter.fromJson(value)
    }
}