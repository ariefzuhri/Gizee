package com.ariefzuhri.gizee.core.common.util

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ariefzuhri.gizee.core.common.R

fun ImageView.loadRounded(url: String?) {
    load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
        transformations(RoundedCornersTransformation(8f))
    }
}

fun RecyclerView.Adapter<*>.isNotEmpty(): Boolean {
    return itemCount > 0
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone(shouldBeGone: Boolean) {
    if (shouldBeGone) visibility = View.GONE
    else visible()
}