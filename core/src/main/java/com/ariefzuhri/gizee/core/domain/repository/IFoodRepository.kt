package com.ariefzuhri.gizee.core.domain.repository

import com.ariefzuhri.gizee.core.data.repository.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import kotlinx.coroutines.flow.Flow

interface IFoodRepository {

    fun getSearchResult(query: String): Flow<Resource<History>>

    fun getNutrients(): Flow<Resource<List<Nutrient>>>

    fun getHistory(): Flow<List<History>>

    fun getFavorites(): Flow<List<Food>>

    fun isFavorite(foodId: String): Flow<Boolean>

    fun setFavorite(food: Food, newState: Boolean)

    fun deleteHistory(history: History)

    fun clearHistory()
}