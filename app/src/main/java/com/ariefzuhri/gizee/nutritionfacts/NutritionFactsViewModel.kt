package com.ariefzuhri.gizee.nutritionfacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class NutritionFactsViewModel(foodUseCase: FoodUseCase) : ViewModel() {

    lateinit var foods: List<Food>

    val nutrients: LiveData<Resource<List<Nutrient>>> =
        foodUseCase.getNutrients().asLiveData()
}