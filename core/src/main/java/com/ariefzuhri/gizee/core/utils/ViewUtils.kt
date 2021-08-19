package com.ariefzuhri.gizee.core.utils

import android.view.View
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat

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

fun Window.setBackground(colorId: Int) {
    val color = ContextCompat.getColor(context, colorId)
    val colorDrawable = ColorDrawable(color)
    setBackgroundDrawable(colorDrawable)
}