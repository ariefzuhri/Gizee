package com.ariefzuhri.gizee.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class DetailsViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    fun isFavorite(id: String): LiveData<Boolean> =
        foodUseCase.isFavorite(id)

    fun setFavorite(food: Food, newState: Boolean) {
        food.isFavorite = newState
        if (newState) foodUseCase.insertFavorite(food)
        else foodUseCase.deleteFavorite(food)
    }
}