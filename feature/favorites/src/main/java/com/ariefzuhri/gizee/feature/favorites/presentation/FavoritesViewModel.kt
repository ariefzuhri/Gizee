package com.ariefzuhri.gizee.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.feature.favorites.domain.usecase.FavoritesUseCase

class FavoritesViewModel(useCase: FavoritesUseCase) : ViewModel() {

    val getFavorites = useCase.getFavorites().asLiveData()
}