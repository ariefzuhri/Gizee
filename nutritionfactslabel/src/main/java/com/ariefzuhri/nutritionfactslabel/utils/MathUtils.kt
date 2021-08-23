package com.ariefzuhri.nutritionfactslabel.utils

import java.text.DecimalFormat

internal fun Double?.divideToPercent(divideTo: Double): String {
    return if (divideTo == 0.0) "0%"
    else "${((this ?: 0.0) * 100 / divideTo).toDecimal()}%"
}

internal fun Number?.toDecimal(): String {
    DecimalFormat("0.#").format(this ?: 0)
}