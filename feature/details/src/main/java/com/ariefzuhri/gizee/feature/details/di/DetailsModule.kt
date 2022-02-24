package com.ariefzuhri.gizee.feature.details.di

import com.ariefzuhri.gizee.feature.details.data.DetailsRepository
import com.ariefzuhri.gizee.feature.details.domain.repository.IDetailsRepository
import com.ariefzuhri.gizee.feature.details.domain.usecase.DetailsInteractor
import com.ariefzuhri.gizee.feature.details.domain.usecase.DetailsUseCase
import com.ariefzuhri.gizee.feature.details.presentation.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

internal object DetailsModule {

    private val modules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(modules)

    internal fun unload() = unloadKoinModules(modules)
}

private val repositoryModule = module {
    single<IDetailsRepository> {
        DetailsRepository(
            localDataSource = get(),
            appExecutors = get(),
        )
    }
}

private val useCaseModule = module {
    factory<DetailsUseCase> {
        DetailsInteractor(repository = get())
    }
}

private val viewModelModule = module {
    viewModel {
        DetailsViewModel(useCase = get())
    }
}