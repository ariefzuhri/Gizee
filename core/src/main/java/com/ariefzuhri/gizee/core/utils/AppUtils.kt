package com.ariefzuhri.gizee.core.utils

import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import com.ariefzuhri.gizee.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.DecimalFormat

object AppUtils {

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

    fun getDecimalFormat(number: Number?): String {
        return DecimalFormat("0.#").format(number ?: 0)
    }

    fun loadImage(context: Context?, imageView: ImageView, source: Any?) {
        context?.let {
            Glide.with(it.applicationContext)
                .asBitmap()
                .load(source ?: "")
                .apply(myGlideOptions())
                .centerCrop()
                .into(imageView)
        }
    }

    private fun myGlideOptions(): RequestOptions {
        return RequestOptions.placeholderOf(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
    }

    fun showToast(context: Context?, message: CharSequence?) {
        Toast.makeText(context, message ?: "", Toast.LENGTH_LONG).show()
    }

    fun showToast(context: Context?, messageId: Int) {
        Toast.makeText(context, context?.getString(messageId), Toast.LENGTH_LONG).show()
    }
}