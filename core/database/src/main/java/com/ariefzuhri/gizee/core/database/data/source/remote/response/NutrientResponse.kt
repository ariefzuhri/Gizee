package com.ariefzuhri.gizee.core.database.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NutrientResponse(

    @field:Json(name = "attr_id")
    val attributeId: Int? = null,

    @field:Json(name = "unit")
    val unit: String? = null,

    @field:Json(name = "usda_nutr_desc")
    val usdaNutrientDesc: String? = null,
)