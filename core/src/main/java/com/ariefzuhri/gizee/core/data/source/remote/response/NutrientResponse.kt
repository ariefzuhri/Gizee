package com.ariefzuhri.gizee.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class NutrientResponse(

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("api_name")
    val apiName: String? = null,

    @field:SerializedName("usda_tag")
    val usdaTag: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("fda_daily_value")
    val fdaDailyValue: Int? = null,

    @field:SerializedName("usda_nutr_desc")
    val usdaNutrDesc: String? = null,

    @field:SerializedName("usda_sr_order")
    val usdaSrOrder: Int? = null,

    @field:SerializedName("attr_id")
    val attrId: Int? = null
)