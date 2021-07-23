package com.ariefzuhri.gizee.details

import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.data.FoodRepository
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity

class DetailsViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    fun getNutrients() = foodRepository.getNutrients()

    fun isFavorite(id: String) = foodRepository.isFavorite(id)

    fun setFavorite(food: FoodEntity, newState: Boolean) {
        food.isFavorite = newState
        if (newState) foodRepository.insertFavorite(food)
        else foodRepository.deleteFavorite(food)
    }
}