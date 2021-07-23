@file:Suppress("unused")

package com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel

import android.content.res.Resources

object DimensionUtils {

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }
}