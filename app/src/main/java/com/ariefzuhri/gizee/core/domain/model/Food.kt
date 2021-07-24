package com.ariefzuhri.gizee.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    var id: String? = null,
    var name: String? = null,
    var photo: String? = null,
    var servingQty: Double? = null,
    var servingUnit: String? = null,
    var servingWeightGrams: Double? = null,
    var nfCalories: Double? = null,
    var nfTotalFat: Double? = null,
    var nfSaturatedFat: Double? = null,
    var nfCholesterol: Double? = null,
    var nfSodium: Double? = null,
    var nfTotalCarbohydrate: Double? = null,
    var nfDietaryFiber: Double? = null,
    var nfSugars: Double? = null,
    var nfProtein: Double? = null,
    var nfPotassium: Double? = null,
    var nfP: Double? = null,
    var fullNutrients: List<Nutrient?>? = null,
    var isFavorite: Boolean? = null
) : Parcelable