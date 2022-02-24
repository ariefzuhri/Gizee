package com.ariefzuhri.gizee.feature.favorites.domain.usecase

import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.feature.favorites.domain.repository.IFavoritesRepository
import kotlinx.coroutines.flow.Flow

class FavoritesInteractor(private val repository: IFavoritesRepository) : FavoritesUseCase {

    override fun getFavorites(): Flow<List<Food>> {
        return repository.getFavorites()
    }
}