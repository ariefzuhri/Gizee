package com.ariefzuhri.gizee.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey @NonNull val id: String,
    val name: String,
    val photo: String,
    val servingQty: Double,
    val servingUnit: String,
    val servingWeightGrams: Double,
    val nfCalories: Double,
    val nfTotalFat: Double,
    val nfSaturatedFat: Double,
    val nfTransFat: Double,
    val nfPolyFat: Double,
    val nfMonoFat: Double,
    val nfCholesterol: Double,
    val nfSodium: Double,
    val nfTotalCarbohydrate: Double,
    val nfDietaryFiber: Double,
    val nfSugars: Double,
    val nfProtein: Double,
    val nfVitA: Double,
    val nfVitB6: Double,
    val nfVitC: Double,
    val nfVitD: Double,
    val nfCalcium: Double,
    val nfIron: Double,
    val nfPotassium: Double,
    val nfFolate: Double,
    val fullNutrients: List<NutrientEntity>
)