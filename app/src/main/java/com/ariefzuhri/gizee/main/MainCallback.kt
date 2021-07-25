package com.ariefzuhri.gizee.main

import com.ariefzuhri.gizee.core.domain.model.History

interface MainCallback {

    fun openSearchResult(query: String)

    fun getQueryFromHistory(history: History)
}