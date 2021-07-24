package com.ariefzuhri.gizee.core.domain.usecase

import androidx.lifecycle.LiveData
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient

interface FoodUseCase {

    fun searchFoodsByNaturalLanguage(query: String): LiveData<Resource<List<Food>>>

    fun getFavorites(): LiveData<List<Food>>

    fun isFavorite(id: String): LiveData<Food>

    fun getHistory(): LiveData<List<History>>

    fun getNutrients(): LiveData<Resource<List<Nutrient>>>

    fun insertFavorite(food: Food)

    fun insertHistory(history: History)

    fun deleteFavorite(food: Food)

    fun deleteHistory(history: History)

    fun clearHistory()
}