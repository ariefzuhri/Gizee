package com.ariefzuhri.gizee.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "foods")
data class FoodEntity(
    @PrimaryKey
    @NonNull
    var id: String,
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
    var fullNutrients: List<NutrientEntity?>? = null,
    var isFavorite: Boolean? = null
) : Parcelable