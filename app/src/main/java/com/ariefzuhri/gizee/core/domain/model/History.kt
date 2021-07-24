package com.ariefzuhri.gizee.core.domain.model

import java.util.*

data class History(
    var query: String? = null,
    var timestamp: Long? = null
) {

    constructor(query: String) : this(
        query.lowercase().replace("\\s+", " "),
        Calendar.getInstance().timeInMillis
    )
}