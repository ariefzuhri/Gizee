package com.ariefzuhri.gizee.main.searchresult

import androidx.lifecycle.*
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class SearchResultViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    private val query = MutableLiveData<String>()

    fun performQuery(query: String) {
        this.query.value = query
    }

    val searchResult: LiveData<Resource<History>> =
        Transformations.switchMap(query) {
            foodUseCase.getSearchResult(it).asLiveData()
        }
}