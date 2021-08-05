package com.ariefzuhri.gizee.core.utils

import android.os.Build

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