package com.ariefzuhri.gizee.feature.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.feature.details.domain.usecase.DetailsUseCase
import com.ariefzuhri.gizee.core.database.domain.model.Food

class DetailsViewModel(private val useCase: DetailsUseCase) : ViewModel() {

    var food: Food? = null

    fun isFavorite(): LiveData<Boolean> {
        return useCase.isFavorite(food!!.id).asLiveData()
    }

    fun setNewStateFavorite() {
        val newState = !food!!.isFavorite
        useCase.setFavorite(food!!, newState)
    }
}