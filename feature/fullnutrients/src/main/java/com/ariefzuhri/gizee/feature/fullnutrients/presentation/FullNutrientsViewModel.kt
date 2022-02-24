package com.ariefzuhri.gizee.feature.fullnutrients.presentation

import androidx.lifecycle.ViewModel
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient

class FullNutrientsViewModel : ViewModel() {

    var nutrients: List<Nutrient>? = null

    fun setNutrients(rawNutrients: List<Nutrient>, foods: List<Food>) {
        nutrients = combinedNutrientValues(rawNutrients, foods)
    }

    /**
     * To display a complete list of nutrients in several foods,
     * we need to combine each nutrient value present in each food.
     */
    private fun combinedNutrientValues(
        rawNutrients: List<Nutrient>,
        foods: List<Food>,
    ): List<Nutrient> {
        // Copy all nutrient data with zero value from raw nutrients to result
        val result = rawNutrients.map {
            Nutrient(it.id,
                it.name,
                it.unit,
                0.0)
        }
        // Combine values of all nutrients in each food
        foods.forEach { food ->
            // Add each nutrient value in foods to rawNutrients by id
            food.fullNutrients.forEach { onlyValueNutrient ->
                result.addValueById(onlyValueNutrient)
            }
        }
        return result
    }

    private fun List<Nutrient>.addValueById(uncombinedNutrient: Nutrient) {
        forEachIndexed { i, combinedNutrient ->
            if (combinedNutrient.id == uncombinedNutrient.id) {
                this[i].value += uncombinedNutrient.value
                return@forEachIndexed
            }
        }
    }
}