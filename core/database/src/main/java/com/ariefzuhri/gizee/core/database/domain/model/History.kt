package com.ariefzuhri.gizee.core.database.domain.model

data class History(
    val query: String,
    val timestamp: Long,
    val foods: List<Food>? = null,
)