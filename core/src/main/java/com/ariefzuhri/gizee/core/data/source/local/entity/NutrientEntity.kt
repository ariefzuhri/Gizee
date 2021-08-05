package com.ariefzuhri.gizee.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "nutrients")
data class NutrientEntity(
    @PrimaryKey @NonNull val id: Int,
    val name: String,
    val unit: String,
    @Ignore val value: Double
) {

    constructor(id: Int, name: String, unit: String) : this(id, name, unit, 0.0)
}