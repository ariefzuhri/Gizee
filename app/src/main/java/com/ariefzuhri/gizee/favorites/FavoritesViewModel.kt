package com.ariefzuhri.gizee.favorites

import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.data.FoodRepository

class FavoritesViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    fun getFavorites() = foodRepository.getFavorites()
}