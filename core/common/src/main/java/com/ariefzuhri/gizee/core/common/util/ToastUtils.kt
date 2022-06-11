package com.ariefzuhri.gizee.core.common.util

import android.app.Activity
import android.widget.Toast

fun Activity?.showToast(message: CharSequence?) {
    Toast.makeText(
        this?.applicationContext,
        message,
        Toast.LENGTH_LONG
    ).show()
}

fun Activity?.showToast(messageId: Int) {
    Toast.makeText(
        this?.applicationContext,
        this?.getString(messageId),
        Toast.LENGTH_LONG
    ).show()
}