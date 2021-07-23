package com.ariefzuhri.gizee.core.utils

import androidx.room.TypeConverter
import com.ariefzuhri.gizee.core.data.source.local.entity.Nutrient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun toString(value: ArrayList<Nutrient?>?): String? {
        val listType = object : TypeToken<ArrayList<Nutrient?>?>() {}.type
        return Gson().toJson(value, listType)
    }

    @TypeConverter
    fun toList(value: String?): ArrayList<Nutrient?>? {
        val listType = object : TypeToken<ArrayList<Nutrient?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}