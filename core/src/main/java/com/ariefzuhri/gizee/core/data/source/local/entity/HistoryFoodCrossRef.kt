package com.ariefzuhri.gizee.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "historyFoodCrossRefs",
    primaryKeys = ["queries", "foodId"],
    foreignKeys = [
        ForeignKey(
            entity = HistoryEntity::class,
            parentColumns = ["queries"],
            childColumns = ["queries"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FoodEntity::class,
            parentColumns = ["id"],
            childColumns = ["foodId"]
        )
    ],
    indices = [
        Index("queries"),
        Index("foodId")
    ]
)
data class HistoryFoodCrossRef(
    @NonNull @ColumnInfo(name = "queries") val query: String,
    @NonNull val foodId: String
)