package com.ariefzuhri.gizee.feature.history.domain.usecase

import com.ariefzuhri.gizee.feature.history.domain.repository.IHistoryRepository
import com.ariefzuhri.gizee.core.database.domain.model.History
import kotlinx.coroutines.flow.Flow

class HistoryInteractor(private val repository: IHistoryRepository) : HistoryUseCase {

    override fun getHistory(): Flow<List<History>> {
        return repository.getHistory()
    }

    override fun clearHistory() {
        return repository.clearHistory()
    }

    override fun deleteHistory(history: History) {
        return repository.deleteHistory(history)
    }
}