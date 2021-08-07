package com.ariefzuhri.gizee.core.utils

import android.content.Context
import android.widget.Toast
import java.io.IOException
import java.text.DecimalFormat

object AppUtils {

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

    fun showToast(context: Context?, message: CharSequence?) {
        Toast.makeText(context, message ?: "", Toast.LENGTH_LONG).show()
    }

    fun showToast(context: Context?, messageId: Int) {
        Toast.makeText(context, context?.getString(messageId), Toast.LENGTH_LONG).show()
    }
}