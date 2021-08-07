package com.ariefzuhri.gizee.core.utils

import android.os.Build
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat


val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) // First 23 chars
                name.substring(0, 23) else name
        } else {
            val name = javaClass.name
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) // Last 23 chars
                name.substring(name.length - 23, name.length) else name
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

fun Window.setBackground(colorId: Int) {
    val color = ContextCompat.getColor(context, colorId)
    val colorDrawable = ColorDrawable(color)
    setBackgroundDrawable(colorDrawable)
}