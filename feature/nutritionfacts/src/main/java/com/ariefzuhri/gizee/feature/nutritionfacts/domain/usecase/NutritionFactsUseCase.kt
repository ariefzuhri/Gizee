package com.ariefzuhri.gizee.feature.nutritionfacts.domain.usecase

import com.ariefzuhri.gizee.core.common.wrapper.Resource
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient
import kotlinx.coroutines.flow.Flow

interface NutritionFactsUseCase {

    fun getNutrients(): Flow<Resource<List<Nutrient>>>
}