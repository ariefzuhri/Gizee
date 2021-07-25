package com.ariefzuhri.gizee.home

import androidx.lifecycle.*
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase

class HomeViewModel(private val foodUseCase: FoodUseCase) : ViewModel() {

    private val query = MutableLiveData<String>()

    fun performQuery(query: String) {
        this.query.value = query
    }

    val getFoodsByNaturalLanguage: LiveData<Resource<List<Food>>> =
        Transformations.switchMap(query) {
            foodUseCase.insertHistory(History(it))
            foodUseCase.getFoodsByNaturalLanguage(it).asLiveData()
        }

    val getHistory =
        foodUseCase.getHistory().asLiveData()

    fun deleteHistory(history: History) =
        foodUseCase.deleteHistory(history)

    fun clearHistory() =
        foodUseCase.clearHistory()
}