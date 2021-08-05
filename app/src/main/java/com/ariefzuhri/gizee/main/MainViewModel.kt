package com.ariefzuhri.gizee.main

import androidx.lifecycle.*
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class MainViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    private val query = MutableLiveData<String>()

    fun performQuery(query: String) {
        this.query.value = query
    }

    val searchFoods: LiveData<Resource<History>> =
        Transformations.switchMap(query) {
            foodUseCase.searchFoods(it).asLiveData()
        }

    val getHistory =
        foodUseCase.getHistory().asLiveData()

    fun clearHistory() =
        foodUseCase.clearHistory()

    fun deleteHistory(history: History) =
        foodUseCase.deleteHistory(history)
}