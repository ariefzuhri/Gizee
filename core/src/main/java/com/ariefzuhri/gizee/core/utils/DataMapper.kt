package com.ariefzuhri.gizee.core.utils

import com.ariefzuhri.gizee.core.data.source.local.entity.*
import com.ariefzuhri.gizee.core.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.data.source.remote.response.FullNutrientsItem
import com.ariefzuhri.gizee.core.data.source.remote.response.NutrientResponse
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.domain.model.Nutrient

private const val NUTRIENT_ID_TRANS_FAT = 605
private const val NUTRIENT_ID_POLYUNSATURATED_FAT = 646
private const val NUTRIENT_ID_MONOUNSATURATED_FAT = 645
private const val NUTRIENT_ID_VITAMIN_A = 320
private const val NUTRIENT_ID_VITAMIN_B6 = 415
private const val NUTRIENT_ID_VITAMIN_C = 401
private const val NUTRIENT_ID_VITAMIN_D = 328
private const val NUTRIENT_ID_CALCIUM = 301
private const val NUTRIENT_ID_IRON = 303
private const val NUTRIENT_ID_FOLATE = 435

object FoodMapper {

    fun mapResponseToEntities(input: FoodResponse): List<FoodEntity> {
        val result = arrayListOf<FoodEntity>()
        input.foods?.forEach {
            val fullNutrients = arrayListOf<NutrientEntity>()
            it?.fullNutrients?.forEach { item ->
                val nutrientEntity = NutrientEntity(
                    id = item?.attrId!!,
                    name = "",
                    unit = "",
                    value = item.value ?: 0.0
                )
                fullNutrients.add(nutrientEntity)
            }

            val foodEntity = FoodEntity(
                id = "${it?.foodName}_${it?.servingWeightGrams}",
                name = it?.foodName ?: "",
                photo = it?.photo?.thumb ?: "",
                servingQty = it?.servingQty ?: 0.0,
                servingUnit = it?.servingUnit ?: "",
                servingWeightGrams = it?.servingWeightGrams ?: 0.0,
                nfCalories = it?.nfCalories ?: 0.0,
                nfTotalFat = it?.nfTotalFat ?: 0.0,
                nfSaturatedFat = it?.nfSaturatedFat ?: 0.0,
                nfTransFat = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_TRANS_FAT),
                nfPolyFat = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_POLYUNSATURATED_FAT),
                nfMonoFat = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_MONOUNSATURATED_FAT),
                nfCholesterol = it?.nfCholesterol ?: 0.0,
                nfSodium = it?.nfSodium ?: 0.0,
                nfTotalCarbohydrate = it?.nfTotalCarbohydrate ?: 0.0,
                nfDietaryFiber = it?.nfDietaryFiber ?: 0.0,
                nfSugars = it?.nfSugars ?: 0.0,
                nfProtein = it?.nfProtein ?: 0.0,
                nfVitA = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_A),
                nfVitB6 = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_B6),
                nfVitC = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_C),
                nfVitD = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_D),
                nfCalcium = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_CALCIUM),
                nfIron = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_IRON),
                nfPotassium = it?.nfPotassium ?: 0.0,
                nfFolate = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_FOLATE),
                fullNutrients = fullNutrients,
                isFavorite = false
            )

            result.add(foodEntity)
        }
        return result
    }

    fun mapEntitiesToDomain(input: List<FoodEntity>): List<Food> {
        val result = arrayListOf<Food>()
        input.forEach { result.add(mapEntityToDomain(it)) }
        return result
    }

    private fun mapEntityToDomain(input: FoodEntity): Food {
        return Food(
            id = input.id,
            name = input.name,
            photo = input.photo,
            servingQty = input.servingQty,
            servingUnit = input.servingUnit,
            servingWeightGrams = input.servingWeightGrams,
            nfCalories = input.nfCalories,
            nfTotalFat = input.nfTotalFat,
            nfSaturatedFat = input.nfSaturatedFat,
            nfTransFat = input.nfTransFat,
            nfPolyFat = input.nfPolyFat,
            nfMonoFat = input.nfMonoFat,
            nfCholesterol = input.nfCholesterol,
            nfSodium = input.nfSodium,
            nfTotalCarbohydrate = input.nfTotalCarbohydrate,
            nfDietaryFiber = input.nfDietaryFiber,
            nfSugars = input.nfSugars,
            nfProtein = input.nfProtein,
            nfVitA = input.nfVitA,
            nfVitB6 = input.nfVitB6,
            nfVitC = input.nfVitC,
            nfVitD = input.nfVitD,
            nfCalcium = input.nfCalcium,
            nfIron = input.nfIron,
            nfPotassium = input.nfPotassium,
            nfFolate = input.nfFolate,
            fullNutrients = NutrientMapper.mapEntitiesToDomain(input.fullNutrients),
            isFavorite = input.isFavorite
        )
    }

    private fun getNutrientValue(nutrients: List<FullNutrientsItem?>?, id: Int): Double {
        return nutrients?.find { it?.attrId == id }?.value ?: 0.0
    }
}

object HistoryMapper {

    fun mapEntitiesToDomain(input: List<HistoryEntity>): List<History> {
        val result = arrayListOf<History>()
        input.forEach {
            val history = History(
                query = it.query,
                timestamp = it.timestamp
            )
            result.add(history)
        }
        return result
    }

    fun mapEntityToDomain(input: HistoryWithFoods): History {
        return History(
            query = input.history.query,
            timestamp = input.history.timestamp,
            foods = FoodMapper.mapEntitiesToDomain(input.foods)
        )
    }

    fun mapDomainToEntity(input: History): HistoryEntity {
        return HistoryEntity(
            query = input.query,
            timestamp = input.timestamp
        )
    }
}

object NutrientMapper {

    fun mapResponsesToEntities(input: List<NutrientResponse>): List<NutrientEntity> {
        val result = arrayListOf<NutrientEntity>()
        input.forEach {
            val nutrient = NutrientEntity(
                id = it.attrId!!,
                name = it.usdaNutrDesc ?: "",
                unit = it.unit ?: ""
            )
            result.add(nutrient)
        }
        return result
    }

    fun mapEntitiesToDomain(input: List<NutrientEntity>): List<Nutrient> {
        val result = arrayListOf<Nutrient>()
        input.forEach {
            val nutrient = Nutrient(
                id = it.id,
                name = it.name,
                unit = it.unit,
                value = it.value
            )
            result.add(nutrient)
        }
        return result
    }
}