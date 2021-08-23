package com.ariefzuhri.gizee.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResponse(

    @field:Json(name = "foods")
    val foods: List<FoodsItem?>? = null,
)

@JsonClass(generateAdapter = true)
data class Photo(

    @field:Json(name = "thumb")
    val thumb: String? = null,
)

@JsonClass(generateAdapter = true)
data class FoodsItem(

    @field:Json(name = "food_name")
    val foodName: String? = null,

    @field:Json(name = "serving_qty")
    val servingQty: Double? = null,

    @field:Json(name = "serving_unit")
    val servingUnit: String? = null,

    @field:Json(name = "serving_weight_grams")
    val servingWeightGrams: Double? = null,

    @field:Json(name = "nf_calories")
    val nfCalories: Double? = null,

    @field:Json(name = "nf_total_fat")
    val nfTotalFat: Double? = null,

    @field:Json(name = "nf_saturated_fat")
    val nfSaturatedFat: Double? = null,

    @field:Json(name = "nf_cholesterol")
    val nfCholesterol: Double? = null,

    @field:Json(name = "nf_sodium")
    val nfSodium: Double? = null,

    @field:Json(name = "nf_total_carbohydrate")
    val nfTotalCarbohydrate: Double? = null,

    @field:Json(name = "nf_dietary_fiber")
    val nfDietaryFiber: Double? = null,

    @field:Json(name = "nf_sugars")
    val nfSugars: Double? = null,

    @field:Json(name = "nf_protein")
    val nfProtein: Double? = null,

    @field:Json(name = "nf_potassium")
    val nfPotassium: Double? = null,

    @field:Json(name = "full_nutrients")
    val fullNutrients: List<FullNutrientsItem?>? = null,

    @field:Json(name = "photo")
    val photo: Photo? = null,
)

@JsonClass(generateAdapter = true)
data class FullNutrientsItem(

    @field:Json(name = "attr_id")
    val attrId: Int? = null,

    @field:Json(name = "value")
    val value: Double? = null,
)