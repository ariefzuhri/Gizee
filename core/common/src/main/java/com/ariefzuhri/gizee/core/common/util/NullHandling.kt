package com.ariefzuhri.gizee.core.common.util

fun Number?.orEmpty(): Number {
    return this ?: 0
}