package com.ariefzuhri.nutritionfactslabel.utils

import android.content.res.Resources

/*internal fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}*/

internal fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}