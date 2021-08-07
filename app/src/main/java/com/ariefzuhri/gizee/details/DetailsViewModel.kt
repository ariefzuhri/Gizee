package com.ariefzuhri.gizee.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class DetailsViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    private val _food = MutableLiveData<Food>()
    val food: LiveData<Food>
        get() = _food

    fun setFood(food: Food) {
        _food.value = food
    }

    fun setNewStateFavorite() {
        val food = _food.value!!
        val newState = !food.isFavorite
        food.isFavorite = newState
        foodUseCase.updateFavorite(food.id, newState)
        setFood(food)
    }
}