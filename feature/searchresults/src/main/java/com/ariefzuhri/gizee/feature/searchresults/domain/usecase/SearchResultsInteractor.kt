package com.ariefzuhri.gizee.feature.searchresults.domain.usecase

import com.ariefzuhri.gizee.feature.searchresults.domain.repository.ISearchResultsRepository
import com.ariefzuhri.gizee.core.common.wrapper.Resource
import com.ariefzuhri.gizee.core.database.domain.model.History
import kotlinx.coroutines.flow.Flow

class SearchResultsInteractor(private val repository: ISearchResultsRepository) :
    SearchResultsUseCase {

    override fun getSearchResults(query: String): Flow<Resource<History>> {
        return repository.getSearchResults(query)
    }
}