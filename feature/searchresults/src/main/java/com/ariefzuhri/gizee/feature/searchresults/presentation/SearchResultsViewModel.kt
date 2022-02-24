package com.ariefzuhri.gizee.feature.searchresults.presentation

import androidx.lifecycle.*
import com.ariefzuhri.gizee.feature.searchresults.domain.usecase.SearchResultsUseCase
import com.ariefzuhri.gizee.core.common.dto.Resource
import com.ariefzuhri.gizee.core.database.domain.model.History

class SearchResultsViewModel(private val useCase: SearchResultsUseCase) : ViewModel() {

    private val query = MutableLiveData<String>()

    fun performQuery(query: String) {
        this.query.value = query
    }

    val searchResults: LiveData<Resource<History>> =
        Transformations.switchMap(query) {
            useCase.getSearchResults(it).asLiveData()
        }
}