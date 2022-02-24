package com.ariefzuhri.gizee.feature.nutritionfacts.di

import com.ariefzuhri.gizee.feature.nutritionfacts.data.NutritionFactsRepository
import com.ariefzuhri.gizee.feature.nutritionfacts.domain.repository.INutritionFactsRepository
import com.ariefzuhri.gizee.feature.nutritionfacts.domain.usecase.NutritionFactsInteractor
import com.ariefzuhri.gizee.feature.nutritionfacts.domain.usecase.NutritionFactsUseCase
import com.ariefzuhri.gizee.feature.nutritionfacts.presentation.NutritionFactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

internal object NutritionFactsModule {

    private val modules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(modules)

    internal fun unload() = unloadKoinModules(modules)
}

private val repositoryModule = module {
    single<INutritionFactsRepository> {
        NutritionFactsRepository(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}

private val useCaseModule = module {
    factory<NutritionFactsUseCase> {
        NutritionFactsInteractor(repository = get())
    }
}

private val viewModelModule = module {
    viewModel {
        NutritionFactsViewModel(useCase = get())
    }
}