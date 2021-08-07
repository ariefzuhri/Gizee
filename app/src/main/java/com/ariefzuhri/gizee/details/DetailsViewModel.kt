package com.ariefzuhri.gizee.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class DetailsViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    var food: Food? = null

    fun isFavorite(): LiveData<Boolean> {
        return foodUseCase.isFavorite(food!!.id).asLiveData()
    }

    fun setNewStateFavorite() {
        val newState = !food!!.isFavorite
        foodUseCase.setFavorite(food!!, newState)
    }
}