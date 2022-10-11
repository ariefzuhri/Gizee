package com.ariefzuhri.gizee.core.common.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.Adapter<*>.isNotEmpty(): Boolean {
    return itemCount > 0
}