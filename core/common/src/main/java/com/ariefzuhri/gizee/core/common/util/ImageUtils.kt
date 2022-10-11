package com.ariefzuhri.gizee.core.common.util

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ariefzuhri.gizee.core.common.R

fun ImageView.load(source: Any?) {
    load(source) {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
        transformations(RoundedCornersTransformation(8f))
        crossfade(true)
    }
}