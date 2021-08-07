package com.ariefzuhri.gizee.core.domain.usecase

import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.domain.repository.IFoodRepository
import kotlinx.coroutines.flow.Flow

class FoodInteractor(private val foodRepository: IFoodRepository) : FoodUseCase {

    override fun searchFoods(query: String): Flow<Resource<History>> {
        return foodRepository.searchFoods(query)
    }

    override fun getNutrients(): Flow<Resource<List<Nutrient>>> {
        return foodRepository.getNutrients()
    }

    override fun getHistory(): Flow<List<History>> {
        return foodRepository.getHistory()
    }

    override fun getFavorites(): Flow<List<Food>> {
        return foodRepository.getFavorites()
    }

    override fun isFavorite(foodId: String): Flow<Boolean> {
        return foodRepository.isFavorite(foodId)
    }

    override fun setFavorite(food: Food, newState: Boolean) {
        return foodRepository.setFavorite(food, newState)
    }

    override fun clearHistory() {
        return foodRepository.clearHistory()
    }

    override fun deleteHistory(history: History) {
        return foodRepository.deleteHistory(history)
    }
}