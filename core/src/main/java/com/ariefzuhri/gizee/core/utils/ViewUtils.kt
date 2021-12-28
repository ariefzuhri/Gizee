package com.ariefzuhri.gizee.core.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

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