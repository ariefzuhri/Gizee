package com.ariefzuhri.gizee.core.utils

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerHelper constructor(
    private val shimmer: ShimmerFrameLayout,
    private val emptyState: View,
    private vararg val contents: View
) {

    fun show() {
        shimmer.startShimmer()
        shimmer.visibility = View.VISIBLE
        for (content in contents) content.visibility = View.GONE
        emptyState.visibility = View.GONE
    }

    fun hide(isEmpty: Boolean) {
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
        if (isEmpty) {
            emptyState.visibility = View.VISIBLE
        } else {
            for (content in contents) content.visibility = View.VISIBLE
        }
    }
}