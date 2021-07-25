package com.ariefzuhri.gizee.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nutrient(
    var id: Int? = null,
    var name: String? = null,
    var unit: String? = null,
    var value: Double? = null
) : Parcelable {

    constructor(id: Int, name: String?, unit: String?) : this(id, name, unit, 0.0)
}