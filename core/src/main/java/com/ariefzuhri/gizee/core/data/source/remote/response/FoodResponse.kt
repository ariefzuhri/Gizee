package com.ariefzuhri.gizee.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResponse(

    @field:Json(name = "foods")
    val foods: List<FoodsItem?>? = null
)

@JsonClass(generateAdapter = true)
data class Photo(

    @field:Json(name = "thumb")
    val thumb: String? = null,

    @field:Json(name = "highres")
    val highRes: String? = null
)

@JsonClass(generateAdapter = true)
data class AltMeasuresItem(

    @field:Json(name = "serving_weight")
    val servingWeight: Double? = null,

    @field:Json(name = "measure")
    val measure: String? = null,

    @field:Json(name = "qty")
    val qty: Double? = null,

    @field:Json(name = "seq")
    val seq: Int? = null
)

@JsonClass(generateAdapter = true)
data class FoodsItem(

    @field:Json(name = "food_name")
    val foodName: String? = null,

    @field:Json(name = "nf_saturated_fat")
    val nfSaturatedFat: Double? = null,

    @field:Json(name = "metadata")
    val metadata: Metadata? = null,

    @field:Json(name = "nf_cholesterol")
    val nfCholesterol: Double? = null,

    @field:Json(name = "nix_brand_id")
    val nixBrandId: String? = null,

    @field:Json(name = "nf_potassium")
    val nfPotassium: Double? = null,

    @field:Json(name = "meal_type")
    val mealType: Int? = null,

    @field:Json(name = "nf_total_fat")
    val nfTotalFat: Double? = null,

    @field:Json(name = "nf_sugars")
    val nfSugars: Double? = null,

    @field:Json(name = "nf_protein")
    val nfProtein: Double? = null,

    @field:Json(name = "source")
    val source: Int? = null,

    @field:Json(name = "nix_item_id")
    val nixItemId: String? = null,

    @field:Json(name = "ndb_no")
    val ndbNo: Int? = null,

    @field:Json(name = "serving_unit")
    val servingUnit: String? = null,

    @field:Json(name = "alt_measures")
    val altMeasures: List<AltMeasuresItem?>? = null,

    @field:Json(name = "nf_p")
    val nfP: Double? = null,

    @field:Json(name = "lat")
    val lat: Double? = null,

    @field:Json(name = "lng")
    val lng: Double? = null,

    @field:Json(name = "consumed_at")
    val consumedAt: String? = null,

    @field:Json(name = "nix_item_name")
    val nixItemName: String? = null,

    @field:Json(name = "upc")
    val upc: Double? = null,

    @field:Json(name = "photo")
    val photo: Photo? = null,

    @field:Json(name = "brand_name")
    val brandName: String? = null,

    @field:Json(name = "serving_weight_grams")
    val servingWeightGrams: Double? = null,

    @field:Json(name = "nf_total_carbohydrate")
    val nfTotalCarbohydrate: Double? = null,

    @field:Json(name = "full_nutrients")
    val fullNutrients: List<FullNutrientsItem?>? = null,

    @field:Json(name = "tags")
    val tags: Tags? = null,

    @field:Json(name = "nix_brand_name")
    val nixBrandName: String? = null,

    @field:Json(name = "serving_qty")
    val servingQty: Double? = null,

    @field:Json(name = "nf_calories")
    val nfCalories: Double? = null,

    @field:Json(name = "nf_sodium")
    val nfSodium: Double? = null,

    @field:Json(name = "nf_dietary_fiber")
    val nfDietaryFiber: Double? = null,
)

@JsonClass(generateAdapter = true)
data class Tags(

    @field:Json(name = "item")
    val item: String? = null,

    @field:Json(name = "measure")
    val measure: String? = null,

    @field:Json(name = "quantity")
    val quantity: String? = null,

    @field:Json(name = "tag_id")
    val tagId: Int? = null
)

@JsonClass(generateAdapter = true)
data class Metadata(
    val any: Any? = null
)

@JsonClass(generateAdapter = true)
data class FullNutrientsItem(

    @field:Json(name = "value")
    val value: Double? = null,

    @field:Json(name = "attr_id")
    val attrId: Int? = null
)