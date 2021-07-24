package com.ariefzuhri.gizee.home

import com.ariefzuhri.gizee.core.domain.model.History

interface HomeCallback {

    fun openSearchResult(query: String)

    fun getQueryFromHistory(history: History)
}