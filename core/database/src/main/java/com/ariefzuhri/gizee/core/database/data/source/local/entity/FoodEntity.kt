package com.ariefzuhri.gizee.core.database.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey @NonNull val id: String,
    val name: String,
    val photo: String,
    val servingQuantity: Double,
    val servingUnit: String,
    val servingWeightGrams: Double,
    val nfCalories: Double,
    val nfTotalFat: Double,
    val nfSaturatedFat: Double,
    val nfTransFat: Double,
    val nfPolyunsaturatedFat: Double,
    val nfMonounsaturatedFat: Double,
    val nfCholesterol: Double,
    val nfSodium: Double,
    val nfTotalCarbohydrate: Double,
    val nfDietaryFiber: Double,
    val nfSugars: Double,
    val nfProtein: Double,
    val nfVitaminA: Double,
    val nfVitaminB6: Double,
    val nfVitaminC: Double,
    val nfVitaminD: Double,
    val nfCalcium: Double,
    val nfIron: Double,
    val nfPotassium: Double,
    val nfFolate: Double,
    val fullNutrients: List<NutrientEntity>,
)