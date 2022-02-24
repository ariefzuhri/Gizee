package com.ariefzuhri.gizee.feature.history.di

import com.ariefzuhri.gizee.feature.history.data.HistoryRepository
import com.ariefzuhri.gizee.feature.history.domain.repository.IHistoryRepository
import com.ariefzuhri.gizee.feature.history.domain.usecase.HistoryInteractor
import com.ariefzuhri.gizee.feature.history.domain.usecase.HistoryUseCase
import com.ariefzuhri.gizee.feature.history.presentation.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

internal object HistoryModule {

    private val modules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(modules)

    internal fun unload() = unloadKoinModules(modules)
}

private val repositoryModule = module {
    single<IHistoryRepository> {
        HistoryRepository(
            localDataSource = get(),
            appExecutors = get()
        )
    }
}

private val useCaseModule = module {
    factory<HistoryUseCase> {
        HistoryInteractor(repository = get())
    }
}

private val viewModelModule = module {
    viewModel {
        HistoryViewModel(useCase = get())
    }
}