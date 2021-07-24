package com.ariefzuhri.gizee.nutritionfacts

import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.data.FoodRepository

class NutritionFactsViewModel(private val foodRepository: FoodRepository) : ViewModel() {

    fun getNutrients() =
        foodRepository.getNutrients()
}