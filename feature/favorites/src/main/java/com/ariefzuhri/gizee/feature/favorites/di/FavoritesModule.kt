package com.ariefzuhri.gizee.feature.favorites.di

import com.ariefzuhri.gizee.feature.favorites.data.FavoritesRepository
import com.ariefzuhri.gizee.feature.favorites.domain.repository.IFavoritesRepository
import com.ariefzuhri.gizee.feature.favorites.domain.usecase.FavoritesInteractor
import com.ariefzuhri.gizee.feature.favorites.domain.usecase.FavoritesUseCase
import com.ariefzuhri.gizee.feature.favorites.presentation.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

internal object FavoritesModule {

    private val modules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(modules)

    internal fun unload() = unloadKoinModules(modules)
}

private val repositoryModule = module {
    single<IFavoritesRepository> {
        FavoritesRepository(
            localDataSource = get()
        )
    }
}

private val useCaseModule = module {
    factory<FavoritesUseCase> {
        FavoritesInteractor(repository = get())
    }
}

private val viewModelModule = module {
    viewModel {
        FavoritesViewModel(useCase = get())
    }
}