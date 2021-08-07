package com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel

import java.text.DecimalFormat

object MathUtils {

    fun calculatePercentage(obtained: Double, total: Double) =
        "${formatToDecimal(obtained * 100 / total)}%"

    fun formatToDecimal(number: Number?): String =
        DecimalFormat("0.#").format(number ?: 0)
}