package com.ariefzuhri.gizee.core.utils

import android.widget.ImageView
import com.ariefzuhri.gizee.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ViewBinding {

    fun bindLoadImage(imageView: ImageView, source: Any?) {
        Glide.with(imageView.context.applicationContext)
            .asBitmap()
            .load(source ?: "")
            .apply(myGlideOptions())
            .centerCrop()
            .into(imageView)
    }

    private fun myGlideOptions(): RequestOptions {
        return RequestOptions.placeholderOf(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
    }
}