package com.ariefzuhri.gizee.core.database.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Nutrient(
    val id: Int,
    val name: String,
    val unit: String,
    var value: Double,
) : Parcelable