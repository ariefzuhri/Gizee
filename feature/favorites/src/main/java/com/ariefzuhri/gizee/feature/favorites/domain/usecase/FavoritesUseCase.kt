package com.ariefzuhri.gizee.feature.favorites.domain.usecase

import com.ariefzuhri.gizee.core.database.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface FavoritesUseCase {

    fun getFavorites(): Flow<List<Food>>
}