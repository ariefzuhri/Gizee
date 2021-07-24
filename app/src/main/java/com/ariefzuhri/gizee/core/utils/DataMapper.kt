package com.ariefzuhri.gizee.core.utils

import com.ariefzuhri.gizee.core.data.source.local.entity.*
import com.ariefzuhri.gizee.core.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient

object FoodMapper {

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

    fun mapEntitiesToDomain(input: List<FoodEntity>): List<Food> {
        val foods = arrayListOf<Food>()
        input.map {
            val fullNutrients = arrayListOf<Nutrient?>()
            it.fullNutrients?.map { item ->
                val nutrient = Nutrient(
                    id = item?.id,
                    name = item?.name,
                    unit = item?.unit,
                    value = item?.value
                )
                fullNutrients.add(nutrient)
            }

            val food = Food(
                id = it.id,
                name = it.name,
                photo = it.photo,
                servingQty = it.servingQty,
                servingUnit = it.servingUnit,
                servingWeightGrams = it.servingWeightGrams,
                nfCalories = it.nfCalories,
                nfTotalFat = it.nfTotalFat,
                nfSaturatedFat = it.nfSaturatedFat,
                nfCholesterol = it.nfCholesterol,
                nfSodium = it.nfSodium,
                nfTotalCarbohydrate = it.nfTotalCarbohydrate,
                nfDietaryFiber = it.nfDietaryFiber,
                nfSugars = it.nfSugars,
                nfProtein = it.nfProtein,
                nfPotassium = it.nfPotassium,
                nfP = it.nfP,
                fullNutrients = fullNutrients,
                isFavorite = it.isFavorite
            )

            foods.add(food)
        }
        return foods
    }

    fun mapDomainToEntity(input: Food): FoodEntity {
        val fullNutrients = arrayListOf<NutrientEntity?>()
        input.fullNutrients?.map { it ->
            val nutrient = NutrientEntity(
                id = it?.id!!,
                value = it.value
            )
            fullNutrients.add(nutrient)
        }

        return FoodEntity(
            id = input.id!!,
            name = input.name,
            photo = input.photo,
            servingQty = input.servingQty,
            servingUnit = input.servingUnit,
            servingWeightGrams = input.servingWeightGrams,
            nfCalories = input.nfCalories,
            nfTotalFat = input.nfTotalFat,
            nfSaturatedFat = input.nfSaturatedFat,
            nfCholesterol = input.nfCholesterol,
            nfSodium = input.nfSodium,
            nfTotalCarbohydrate = input.nfTotalCarbohydrate,
            nfDietaryFiber = input.nfDietaryFiber,
            nfSugars = input.nfSugars,
            nfProtein = input.nfProtein,
            nfPotassium = input.nfPotassium,
            nfP = input.nfP,
            fullNutrients = fullNutrients,
            isFavorite = input.isFavorite
        )
    }
}

object HistoryMapper {

    fun mapEntitiesToDomain(input: List<HistoryEntity>): List<History> {
        val result = arrayListOf<History>()
        input.map {
            val history = History(
                query = it.query,
                timestamp = it.timestamp
            )
            result.add(history)
        }
        return result
    }

    fun mapDomainToEntity(input: History): HistoryEntity {
        return HistoryEntity(
            query = input.query!!,
            timestamp = input.timestamp
        )
    }
}

object NutrientMapper {

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

    fun mapEntitiesToDomain(input: List<NutrientEntity>): List<Nutrient> {
        val nutrients = arrayListOf<Nutrient>()
        input.map {
            val nutrient = Nutrient(
                id = it.id,
                name = it.name,
                unit = it.unit
            )
            nutrients.add(nutrient)
        }
        return nutrients
    }
}