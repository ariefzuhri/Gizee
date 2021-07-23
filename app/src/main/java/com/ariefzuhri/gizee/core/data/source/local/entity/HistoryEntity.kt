package com.ariefzuhri.gizee.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey
    @NonNull
    var query: String,
    var timestamp: Long? = null
) {

    @Ignore
    constructor(query: String) : this(
        query.lowercase().replace("\\s+", " "),
        Calendar.getInstance().timeInMillis
    )
}