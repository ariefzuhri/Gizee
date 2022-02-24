package com.ariefzuhri.gizee.feature.details.domain.usecase

import com.ariefzuhri.gizee.core.database.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface DetailsUseCase {

    fun isFavorite(foodId: String): Flow<Boolean>

    fun setFavorite(food: Food, newState: Boolean)
}