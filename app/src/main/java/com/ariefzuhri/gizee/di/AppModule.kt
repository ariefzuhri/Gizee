package com.ariefzuhri.gizee.di

import com.ariefzuhri.gizee.core.domain.usecase.FoodInteractor
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase
import com.ariefzuhri.gizee.details.DetailsViewModel
import com.ariefzuhri.gizee.main.MainViewModel
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<FoodUseCase> { FoodInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DetailsViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { NutritionFactsViewModel(get()) }
}