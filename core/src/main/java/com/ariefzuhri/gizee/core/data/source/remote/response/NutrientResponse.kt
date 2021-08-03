package com.ariefzuhri.gizee.core.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NutrientResponse(

    @field:Json(name = "unit")
    val unit: String? = null,

    @field:Json(name = "api_name")
    val apiName: String? = null,

    @field:Json(name = "usda_tag")
    val usdaTag: String? = null,

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "fda_daily_value")
    val fdaDailyValue: Int? = null,

    @field:Json(name = "usda_nutr_desc")
    val usdaNutrDesc: String? = null,

    @field:Json(name = "usda_sr_order")
    val usdaSrOrder: Int? = null,

    @field:Json(name = "attr_id")
    val attrId: Int? = null
)