package com.ariefzuhri.gizee.core.domain.repository

import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import kotlinx.coroutines.flow.Flow

interface IFoodRepository {

    fun getFoodsByNaturalLanguage(query: String): Flow<Resource<List<Food>>>

    fun getFavorites(): Flow<List<Food>>

    fun isFavorite(id: String): Flow<Boolean>

    fun getHistory(): Flow<List<History>>

    fun getNutrients(): Flow<Resource<List<Nutrient>>>

    fun insertFavorite(food: Food)

    fun insertHistory(history: History)

    fun deleteFavorite(food: Food)

    fun deleteHistory(history: History)

    fun clearHistory()
}