package com.ariefzuhri.gizee.core.common.util

/** Remove redundant whitespaces */
fun String?.cleanup(): String {
    return this?.replace(
        "(?m)(^\\s+|[\\s&&[^\\r\\n]](?=\\s|$)|\\s+\\z)".toRegex(),
        ""
    ).toString()
}

fun String?.getHost(): String {
    return this?.split("/")?.get(2).orEmpty()
}