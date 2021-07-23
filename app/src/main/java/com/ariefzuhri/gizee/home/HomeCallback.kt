package com.ariefzuhri.gizee.home

import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity

interface HomeCallback {

    fun openSearchResult(query: String)

    fun getQueryFromHistory(history: HistoryEntity)
}