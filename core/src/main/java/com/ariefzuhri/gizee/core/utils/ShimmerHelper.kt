package com.ariefzuhri.gizee.core.utils

import android.view.View
import android.widget.LinearLayout
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerHelper constructor(
    private val shimmer: ShimmerFrameLayout,
    private val layoutContent: LinearLayout,
    private val layoutEmpty: LinearLayout
) {

    fun show() {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        layoutContent.visibility = View.INVISIBLE
        layoutEmpty.visibility = View.INVISIBLE
    }

    fun hide(isEmpty: Boolean) {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        if (isEmpty) {
            layoutEmpty.visibility = View.VISIBLE
        } else {
            layoutContent.visibility = View.VISIBLE
        }
    }
}