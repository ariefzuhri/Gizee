package com.ariefzuhri.gizee.library.nutritionfactslabel.util

import android.content.res.Resources

internal fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}