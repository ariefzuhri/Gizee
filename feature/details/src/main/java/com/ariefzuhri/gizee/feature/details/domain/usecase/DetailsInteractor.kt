package com.ariefzuhri.gizee.feature.details.domain.usecase

import com.ariefzuhri.gizee.feature.details.domain.repository.IDetailsRepository
import com.ariefzuhri.gizee.core.database.domain.model.Food
import kotlinx.coroutines.flow.Flow

class DetailsInteractor(private val repository: IDetailsRepository) : DetailsUseCase {

    override fun isFavorite(foodId: String): Flow<Boolean> {
        return repository.isFavorite(foodId)
    }

    override fun setFavorite(food: Food, newState: Boolean) {
        repository.setFavorite(food, newState)
    }
}