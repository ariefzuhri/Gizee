package com.ariefzuhri.gizee.nutritionfacts

import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class NutritionFactsViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    fun getNutrients() =
        foodUseCase.getNutrients()
}