package com.ariefzuhri.gizee.core.utils

import androidx.room.TypeConverter
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun toString(value: List<NutrientEntity?>?): String? {
        val listType = object : TypeToken<List<NutrientEntity?>?>() {}.type
        return Gson().toJson(value, listType)
    }

    @TypeConverter
    fun toList(value: String?): List<NutrientEntity?>? {
        val listType = object : TypeToken<List<NutrientEntity?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}