package com.ariefzuhri.gizee.core.database.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val id: String,
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
    val fullNutrients: List<Nutrient>,
    var isFavorite: Boolean,
) : Parcelable