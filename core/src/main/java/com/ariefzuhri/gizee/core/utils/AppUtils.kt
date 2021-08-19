package com.ariefzuhri.gizee.core.utils

import android.app.Activity
import android.os.Build
import android.widget.Toast
import java.io.IOException
import java.text.DecimalFormat

val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            /* First 23 chars */ name.substring(0, 23) else name
        } else {
            val name = javaClass.name
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            /* Last 23 chars */ name.substring(name.length - 23, name.length) else name
        }
    }

fun Activity.showToast(message: CharSequence?) {
    Toast.makeText(
        applicationContext,
        message,
        Toast.LENGTH_LONG
    ).show()
}

fun Activity.showToast(messageId: Int) {
    Toast.makeText(
        applicationContext,
        getString(messageId),
        Toast.LENGTH_LONG
    ).show()
}

fun formatToDecimal(number: Number?): String {
    return DecimalFormat("0.#").format(number ?: 0)
}

fun isNetworkAvailable(): Boolean {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val exitValue = ipProcess.waitFor()
        return exitValue == 0
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    return false
}