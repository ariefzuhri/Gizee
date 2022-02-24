package com.ariefzuhri.gizee.feature.fullnutrients.di

import com.ariefzuhri.gizee.feature.fullnutrients.presentation.FullNutrientsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

internal object FullNutrientsModule {

    private val modules = listOf(
        viewModelModule,
    )

    internal fun load() = loadKoinModules(modules)

    internal fun unload() = unloadKoinModules(modules)
}

private val viewModelModule = module {
    viewModel {
        FullNutrientsViewModel()
    }
}