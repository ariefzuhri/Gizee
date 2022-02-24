package com.ariefzuhri.gizee.feature.history.domain.usecase

import com.ariefzuhri.gizee.core.database.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryUseCase {

    fun getHistory(): Flow<List<History>>

    fun clearHistory()

    fun deleteHistory(history: History)
}