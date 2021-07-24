package com.ariefzuhri.gizee.core.domain.usecase

import androidx.lifecycle.LiveData
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.domain.repository.IFoodRepository

class FoodInteractor(private val foodRepository: IFoodRepository) : FoodUseCase {

    override fun getFoodsByNaturalLanguage(query: String): LiveData<Resource<List<Food>>> {
        return foodRepository.getFoodsByNaturalLanguage(query)
    }

    override fun getFavorites(): LiveData<List<Food>> {
        return foodRepository.getFavorites()
    }

    override fun isFavorite(id: String): LiveData<Boolean> {
        return foodRepository.isFavorite(id)
    }

    override fun getHistory(): LiveData<List<History>> {
        return foodRepository.getHistory()
    }

    override fun getNutrients(): LiveData<Resource<List<Nutrient>>> {
        return foodRepository.getNutrients()
    }

    override fun insertFavorite(food: Food) {
        return foodRepository.insertFavorite(food)
    }

    override fun insertHistory(history: History) {
        return foodRepository.insertHistory(history)
    }

    override fun deleteFavorite(food: Food) {
        return foodRepository.deleteFavorite(food)
    }

    override fun deleteHistory(history: History) {
        return foodRepository.deleteHistory(history)
    }

    override fun clearHistory() {
        return foodRepository.clearHistory()
    }
}