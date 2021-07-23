package com.ariefzuhri.gizee.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "nutrients")
data class NutrientEntity(
    @PrimaryKey
    @NonNull
    var id: Int,
    var name: String? = null,
    var unit: String? = null,
) : Parcelable