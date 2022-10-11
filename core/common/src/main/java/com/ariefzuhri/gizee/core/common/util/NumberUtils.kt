package com.ariefzuhri.gizee.core.common.util

import java.text.DecimalFormat

fun Number?.toDecimal(): String {
    return DecimalFormat("0.#").format(this.orEmpty())
}