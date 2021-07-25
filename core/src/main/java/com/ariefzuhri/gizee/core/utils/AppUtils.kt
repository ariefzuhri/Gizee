package com.ariefzuhri.gizee.core.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.ariefzuhri.gizee.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.DecimalFormat

object AppUtils {

    fun getDecimalFormat(number: Number?): String {
        return DecimalFormat("0.#").format(number)
    }

    fun loadImage(context: Context, imageView: ImageView, source: Any?) {
        Glide.with(context)
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

    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}