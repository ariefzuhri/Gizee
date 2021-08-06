package com.ariefzuhri.gizee.core.utils

import android.content.Context
import android.widget.Toast
import java.text.DecimalFormat

object AppUtils {

    fun formatToDecimal(number: Number?): String {
        return DecimalFormat("0.#").format(number ?: 0)
    }

    fun showToast(context: Context?, message: CharSequence?) {
        Toast.makeText(context, message ?: "", Toast.LENGTH_LONG).show()
    }

    fun showToast(context: Context?, messageId: Int) {
        Toast.makeText(context, context?.getString(messageId), Toast.LENGTH_LONG).show()
    }
}