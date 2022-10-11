package com.ariefzuhri.gizee.core.common.util

import android.os.Build

val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            /** First 23 chars */
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                name.substring(0, 23) else name
        } else {
            val name = javaClass.name
            /** Last 23 chars */
            if (name.length > 23 && Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                name.substring(name.length - 23, name.length) else name
        }
    }