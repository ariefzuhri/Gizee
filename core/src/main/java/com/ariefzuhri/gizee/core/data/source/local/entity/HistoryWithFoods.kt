package com.ariefzuhri.gizee.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class HistoryWithFoods(
    @Embedded
    val history: HistoryEntity,

    @Relation(
        parentColumn = "queries",
        entity = FoodEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = HistoryFoodCrossRef::class,
            parentColumn = "queries",
            entityColumn = "foodId"
        )
    )
    val foods: List<FoodEntity>
)