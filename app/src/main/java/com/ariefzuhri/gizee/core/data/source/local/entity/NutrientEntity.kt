package com.ariefzuhri.gizee.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
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
    @Ignore
    var value: Double? = null
) : Parcelable {

    constructor(id: Int, name: String?, unit: String?) : this(id, name, unit, 0.0)
}