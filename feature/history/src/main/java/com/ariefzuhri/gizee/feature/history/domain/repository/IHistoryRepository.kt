package com.ariefzuhri.gizee.feature.history.domain.repository

import com.ariefzuhri.gizee.core.database.domain.model.History
import kotlinx.coroutines.flow.Flow

interface IHistoryRepository {

    fun getHistory(): Flow<List<History>>

    fun deleteHistory(history: History)

    fun clearHistory()
}