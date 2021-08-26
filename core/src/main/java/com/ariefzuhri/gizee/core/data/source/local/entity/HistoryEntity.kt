package com.ariefzuhri.gizee.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.ariefzuhri.gizee.core.utils.cleanup
import java.util.*

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey @NonNull @ColumnInfo(name = "queries") val query: String,
    var timestamp: Long,
    @Ignore val foods: List<FoodEntity>? = null,
) {

    constructor(query: String) : this(
        query.lowercase().cleanup(),
        Calendar.getInstance().timeInMillis
    )
}