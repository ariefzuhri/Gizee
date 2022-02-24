package com.ariefzuhri.gizee.core.common.util

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerHelper constructor(
    private val shimmer: ShimmerFrameLayout,
    private val emptyState: View,
    private vararg val contents: View
) {

    fun show() {
        shimmer.startShimmer()
        shimmer.visible()
        emptyState.gone(true)
        for (content in contents) content.gone(true)
    }

    fun hide(isEmpty: Boolean) {
        shimmer.stopShimmer()
        shimmer.gone(true)
        if (isEmpty) emptyState.visible()
        else for (content in contents) content.visible()
    }
}