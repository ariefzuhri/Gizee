package com.ariefzuhri.gizee.di

import com.ariefzuhri.gizee.core.domain.usecase.FoodInteractor
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase
import com.ariefzuhri.gizee.details.DetailsViewModel
import com.ariefzuhri.gizee.main.history.HistoryViewModel
import com.ariefzuhri.gizee.main.searchresult.SearchResultViewModel
import com.ariefzuhri.gizee.fullnutrients.FullNutrientsViewModel
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<FoodUseCase> { FoodInteractor(foodRepository = get()) }
}

val viewModelModule = module {
    viewModel { DetailsViewModel(foodUseCase = get()) }
    viewModel { FullNutrientsViewModel() }
    viewModel { HistoryViewModel(foodUseCase = get()) }
    viewModel { NutritionFactsViewModel(foodUseCase = get()) }
    viewModel { SearchResultViewModel(foodUseCase = get()) }
}