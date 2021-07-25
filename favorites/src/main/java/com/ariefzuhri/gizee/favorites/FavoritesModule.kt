package com.ariefzuhri.gizee.favorites

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel { FavoritesViewModel(get()) }
}