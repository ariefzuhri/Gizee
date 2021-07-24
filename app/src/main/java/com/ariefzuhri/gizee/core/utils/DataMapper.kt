package com.ariefzuhri.gizee.core.utils

import com.ariefzuhri.gizee.core.data.source.local.entity.*
import com.ariefzuhri.gizee.core.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse

object DataMapper {

    fun mapResponseToEntities(input: FoodResponse): List<FoodEntity> {
        val foods = arrayListOf<FoodEntity>()
        input.foods?.map {
            val fullNutrients = arrayListOf<NutrientEntity?>()
            it?.fullNutrients?.map { item ->
                val nutrient = NutrientEntity(
                    id = item?.attrId!!,
                    value = item.value ?: 0.0
                )
                fullNutrients.add(nutrient)
            }

            val food = FoodEntity(
                id = "${it?.foodName}_${it?.servingWeightGrams}",
                name = it?.foodName,
                photo = it?.photo?.thumb,
                servingQty = it?.servingQty,
                servingUnit = it?.servingUnit,
                servingWeightGrams = it?.servingWeightGrams,
                nfCalories = it?.nfCalories,
                nfTotalFat = it?.nfTotalFat,
                nfSaturatedFat = it?.nfSaturatedFat,
                nfCholesterol = it?.nfCholesterol,
                nfSodium = it?.nfSodium,
                nfTotalCarbohydrate = it?.nfTotalCarbohydrate,
                nfDietaryFiber = it?.nfDietaryFiber,
                nfSugars = it?.nfSugars,
                nfProtein = it?.nfProtein,
                nfPotassium = it?.nfPotassium,
                nfP = it?.nfP,
                fullNutrients = fullNutrients,
                isFavorite = false
            )
            foods.add(food)
        }
        return foods
    }

    fun mapResponsesToEntities(input: List<NutrientResponse>): List<NutrientEntity> {
        val nutrients = arrayListOf<NutrientEntity>()
        input.map {
            val nutrient = NutrientEntity(
                id = it.attrId!!,
                name = it.usdaNutrDesc,
                unit = it.unit
            )
            nutrients.add(nutrient)
        }
        return nutrients
    }
}