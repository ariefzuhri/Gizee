package com.ariefzuhri.gizee.nutritionfacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class NutritionFactsViewModel(foodUseCase: FoodUseCase) : ViewModel() {

    val getNutrients = foodUseCase.getNutrients().asLiveData()
}