package com.ariefzuhri.gizee.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class HomeViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    fun searchFoodsByNaturalLanguage(query: String): LiveData<Resource<List<Food>>> =
        foodUseCase.searchFoodsByNaturalLanguage(query)

    fun getHistory() =
        foodUseCase.getHistory()

    fun insertHistory(history: History) =
        foodUseCase.insertHistory(history)

    fun deleteHistory(history: History) =
        foodUseCase.deleteHistory(history)

    fun clearHistory() =
        foodUseCase.clearHistory()
}