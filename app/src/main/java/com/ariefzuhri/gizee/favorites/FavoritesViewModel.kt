package com.ariefzuhri.gizee.favorites

import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class FavoritesViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    fun getFavorites() = foodUseCase.getFavorites()
}