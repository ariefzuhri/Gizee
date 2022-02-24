package com.ariefzuhri.gizee.feature.favorites.domain.repository

import com.ariefzuhri.gizee.core.database.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {

    fun getFavorites(): Flow<List<Food>>
}