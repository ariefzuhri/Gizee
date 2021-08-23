package com.ariefzuhri.gizee.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nutrient(
    val id: Int,
    val name: String,
    val unit: String,
    var value: Double,
) : Parcelable