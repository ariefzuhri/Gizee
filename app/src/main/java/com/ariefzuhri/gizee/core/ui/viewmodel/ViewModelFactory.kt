package com.ariefzuhri.gizee.core.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ariefzuhri.gizee.core.di.Injection
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase
import com.ariefzuhri.gizee.favorites.FavoritesViewModel
import com.ariefzuhri.gizee.details.DetailsViewModel
import com.ariefzuhri.gizee.home.HomeViewModel
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsViewModel

class ViewModelFactory private constructor(private val foodUseCase: FoodUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideFoodUseCase(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                DetailsViewModel(foodUseCase) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(foodUseCase) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(foodUseCase) as T
            }
            modelClass.isAssignableFrom(NutritionFactsViewModel::class.java) -> {
                NutritionFactsViewModel(foodUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}