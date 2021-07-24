package com.ariefzuhri.gizee.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey
    @NonNull
    var query: String,
    var timestamp: Long? = null
)