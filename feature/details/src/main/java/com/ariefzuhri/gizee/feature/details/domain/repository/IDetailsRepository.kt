package com.ariefzuhri.gizee.feature.details.domain.repository

import com.ariefzuhri.gizee.core.database.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface IDetailsRepository {

    fun isFavorite(foodId: String): Flow<Boolean>

    fun setFavorite(food: Food, newState: Boolean)
}