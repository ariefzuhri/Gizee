package com.ariefzuhri.gizee.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(

    @field:SerializedName("foods")
    val foods: List<FoodsItem?>? = null
)

data class Photo(

    @field:SerializedName("thumb")
    val thumb: String? = null,

    @field:SerializedName("highres")
    val highRes: String? = null
)

data class AltMeasuresItem(

    @field:SerializedName("serving_weight")
    val servingWeight: Double? = null,

    @field:SerializedName("measure")
    val measure: String? = null,

    @field:SerializedName("qty")
    val qty: Double? = null,

    @field:SerializedName("seq")
    val seq: Int? = null
)

data class FoodsItem(

    @field:SerializedName("food_name")
    val foodName: String? = null,

    @field:SerializedName("nf_saturated_fat")
    val nfSaturatedFat: Double? = null,

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,

    @field:SerializedName("nf_cholesterol")
    val nfCholesterol: Double? = null,

    @field:SerializedName("nix_brand_id")
    val nixBrandId: String? = null,

    @field:SerializedName("nf_potassium")
    val nfPotassium: Double? = null,

    @field:SerializedName("meal_type")
    val mealType: Int? = null,

    @field:SerializedName("nf_total_fat")
    val nfTotalFat: Double? = null,

    @field:SerializedName("nf_sugars")
    val nfSugars: Double? = null,

    @field:SerializedName("nf_protein")
    val nfProtein: Double? = null,

    @field:SerializedName("source")
    val source: Int? = null,

    @field:SerializedName("nix_item_id")
    val nixItemId: String? = null,

    @field:SerializedName("ndb_no")
    val ndbNo: Int? = null,

    @field:SerializedName("serving_unit")
    val servingUnit: String? = null,

    @field:SerializedName("alt_measures")
    val altMeasures: List<AltMeasuresItem?>? = null,

    @field:SerializedName("nf_p")
    val nfP: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("consumed_at")
    val consumedAt: String? = null,

    @field:SerializedName("nix_item_name")
    val nixItemName: String? = null,

    @field:SerializedName("upc")
    val upc: Double? = null,

    @field:SerializedName("photo")
    val photo: Photo? = null,

    @field:SerializedName("brand_name")
    val brandName: String? = null,

    @field:SerializedName("serving_weight_grams")
    val servingWeightGrams: Double? = null,

    @field:SerializedName("nf_total_carbohydrate")
    val nfTotalCarbohydrate: Double? = null,

    @field:SerializedName("full_nutrients")
    val fullNutrients: List<FullNutrientsItem?>? = null,

    @field:SerializedName("tags")
    val tags: Tags? = null,

    @field:SerializedName("nix_brand_name")
    val nixBrandName: String? = null,

    @field:SerializedName("serving_qty")
    val servingQty: Double? = null,

    @field:SerializedName("nf_calories")
    val nfCalories: Double? = null,

    @field:SerializedName("nf_sodium")
    val nfSodium: Double? = null,

    @field:SerializedName("nf_dietary_fiber")
    val nfDietaryFiber: Double? = null,
)

data class Tags(

    @field:SerializedName("item")
    val item: String? = null,

    @field:SerializedName("measure")
    val measure: String? = null,

    @field:SerializedName("quantity")
    val quantity: String? = null,

    @field:SerializedName("tag_id")
    val tagId: Int? = null
)

data class Metadata(
    val any: Any? = null
)

data class FullNutrientsItem(

    @field:SerializedName("value")
    val value: Double? = null,

    @field:SerializedName("attr_id")
    val attrId: Int? = null
)
