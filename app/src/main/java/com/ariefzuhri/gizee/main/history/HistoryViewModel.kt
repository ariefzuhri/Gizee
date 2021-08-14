package com.ariefzuhri.gizee.main.history

import androidx.lifecycle.*
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class HistoryViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    val history = foodUseCase.getHistory().asLiveData()

    fun clearHistory() = foodUseCase.clearHistory()

    fun deleteHistory(history: History) = foodUseCase.deleteHistory(history)
}