package com.ariefzuhri.gizee.feature.searchresults.di

import com.ariefzuhri.gizee.feature.searchresults.data.SearchResultsRepository
import com.ariefzuhri.gizee.feature.searchresults.domain.repository.ISearchResultsRepository
import com.ariefzuhri.gizee.feature.searchresults.domain.usecase.SearchResultsInteractor
import com.ariefzuhri.gizee.feature.searchresults.domain.usecase.SearchResultsUseCase
import com.ariefzuhri.gizee.feature.searchresults.presentation.SearchResultsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module

internal object SearchResultsModule {

    private val modules = listOf(
        repositoryModule,
        useCaseModule,
        viewModelModule,
    )

    internal fun load() = loadKoinModules(modules)

    internal fun unload() = unloadKoinModules(modules)
}

private val repositoryModule = module {
    single<ISearchResultsRepository> {
        SearchResultsRepository(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }
}

private val useCaseModule = module {
    factory<SearchResultsUseCase> {
        SearchResultsInteractor(repository = get())
    }
}

private val viewModelModule = module {
    viewModel {
        SearchResultsViewModel(useCase = get())
    }
}