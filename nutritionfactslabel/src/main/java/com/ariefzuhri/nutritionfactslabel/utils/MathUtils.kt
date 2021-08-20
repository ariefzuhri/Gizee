package com.ariefzuhri.nutritionfactslabel.utils

import java.text.DecimalFormat

internal fun calculatePercentage(obtained: Double, total: Double) =
    "${(obtained * 100 / total).toDecimal()}%"

internal fun Number?.toDecimal(): String =
    DecimalFormat("0.#").format(this ?: 0)