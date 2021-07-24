package com.ariefzuhri.gizee.home

import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.data.FoodRepository
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity

class HomeViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    fun searchFoodsByNaturalLanguage(query: String) =
        foodRepository.searchFoodsByNaturalLanguage(query)

    fun getHistory() =
        foodRepository.getHistory()

    fun insertHistory(history: HistoryEntity) =
        foodRepository.insertHistory(history)

    fun deleteHistory(history: HistoryEntity) =
        foodRepository.deleteHistory(history)

    fun clearHistory() =
        foodRepository.clearHistory()
}