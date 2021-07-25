package com.ariefzuhri.gizee.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class FavoritesViewModel(foodUseCase: FoodUseCase) : ViewModel() {

    val getFavorites = foodUseCase.getFavorites().asLiveData()
}