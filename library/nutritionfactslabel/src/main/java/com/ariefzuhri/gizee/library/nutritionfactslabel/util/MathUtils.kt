package com.ariefzuhri.gizee.library.nutritionfactslabel.util

import java.text.DecimalFormat

internal fun Double?.divideToPercent(divideTo: Double): String {
    return if (divideTo == 0.0) "0%"
    else "${((this ?: 0.0) * 100 / divideTo).toDecimal()}%"
}

internal fun Number?.toDecimal(): String {
    return DecimalFormat("0.#").format(this ?: 0)
}