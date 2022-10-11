package com.ariefzuhri.gizee.feature.nutritionfacts.domain.usecase

import com.ariefzuhri.gizee.feature.nutritionfacts.domain.repository.INutritionFactsRepository
import com.ariefzuhri.gizee.core.common.wrapper.Resource
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient
import kotlinx.coroutines.flow.Flow

class NutritionFactsInteractor(private val repository: INutritionFactsRepository) :
    NutritionFactsUseCase {

    override fun getNutrients(): Flow<Resource<List<Nutrient>>> {
        return repository.getNutrients()
    }
}