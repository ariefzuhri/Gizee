package com.ariefzuhri.gizee.feature.history.presentation

import androidx.lifecycle.*
import com.ariefzuhri.gizee.feature.history.domain.usecase.HistoryUseCase
import com.ariefzuhri.gizee.core.database.domain.model.History

class HistoryViewModel(private val useCase: HistoryUseCase) : ViewModel() {

    val history = useCase.getHistory().asLiveData()

    fun clearHistory() = useCase.clearHistory()

    fun deleteHistory(history: History) = useCase.deleteHistory(history)
}