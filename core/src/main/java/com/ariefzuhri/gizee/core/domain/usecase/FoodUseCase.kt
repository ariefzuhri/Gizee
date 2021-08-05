package com.ariefzuhri.gizee.core.domain.usecase

import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import kotlinx.coroutines.flow.Flow

interface FoodUseCase {

    fun searchFoods(query: String): Flow<Resource<History>>

    fun getNutrients(): Flow<Resource<List<Nutrient>>>

    fun getHistory(): Flow<List<History>>

    fun getFavorites(): Flow<List<Food>>

    fun updateFavorite(foodId: String, newState: Boolean)

    fun clearHistory()

    fun deleteHistory(history: History)
}