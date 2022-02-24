package com.ariefzuhri.gizee.core.database.util

import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.core.database.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.database.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.database.data.source.remote.response.FoodResponse
import com.ariefzuhri.gizee.core.database.data.source.remote.response.FullNutrientsItem

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

fun mapResponseToEntities(input: FoodResponse): List<FoodEntity> {
    return input.foods?.map {
        val fullNutrients = it?.fullNutrients?.map { item ->
            NutrientEntity(
                id = item?.attributeId!!,
                name = "",
                unit = "",
                value = item.value ?: 0.0
            )
        } ?: listOf()

        FoodEntity(
            id = "${it?.foodName}_${it?.servingWeightGrams}",
            name = it?.foodName ?: "",
            photo = it?.photo?.thumbnail ?: "",
            servingQuantity = it?.servingQuantity ?: 0.0,
            servingUnit = it?.servingUnit ?: "",
            servingWeightGrams = it?.servingWeightGrams ?: 0.0,
            nfCalories = it?.nfCalories ?: 0.0,
            nfTotalFat = it?.nfTotalFat ?: 0.0,
            nfSaturatedFat = it?.nfSaturatedFat ?: 0.0,
            nfTransFat = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_TRANS_FAT),
            nfPolyunsaturatedFat = getNutrientValue(it?.fullNutrients,
                NUTRIENT_ID_POLYUNSATURATED_FAT),
            nfMonounsaturatedFat = getNutrientValue(it?.fullNutrients,
                NUTRIENT_ID_MONOUNSATURATED_FAT),
            nfCholesterol = it?.nfCholesterol ?: 0.0,
            nfSodium = it?.nfSodium ?: 0.0,
            nfTotalCarbohydrate = it?.nfTotalCarbohydrate ?: 0.0,
            nfDietaryFiber = it?.nfDietaryFiber ?: 0.0,
            nfSugars = it?.nfSugars ?: 0.0,
            nfProtein = it?.nfProtein ?: 0.0,
            nfVitaminA = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_A),
            nfVitaminB6 = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_B6),
            nfVitaminC = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_C),
            nfVitaminD = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_VITAMIN_D),
            nfCalcium = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_CALCIUM),
            nfIron = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_IRON),
            nfPotassium = it?.nfPotassium ?: 0.0,
            nfFolate = getNutrientValue(it?.fullNutrients, NUTRIENT_ID_FOLATE),
            fullNutrients = fullNutrients
        )
    } ?: listOf()
}

fun mapEntitiesToDomain(input: List<FoodEntity>): List<Food> {
    return input.map {
        mapEntityToDomain(it)
    }
}

private fun mapEntityToDomain(input: FoodEntity): Food {
    return Food(
        id = input.id,
        name = input.name,
        photo = input.photo,
        servingQuantity = input.servingQuantity,
        servingUnit = input.servingUnit,
        servingWeightGrams = input.servingWeightGrams,
        nfCalories = input.nfCalories,
        nfTotalFat = input.nfTotalFat,
        nfSaturatedFat = input.nfSaturatedFat,
        nfTransFat = input.nfTransFat,
        nfPolyunsaturatedFat = input.nfPolyunsaturatedFat,
        nfMonounsaturatedFat = input.nfMonounsaturatedFat,
        nfCholesterol = input.nfCholesterol,
        nfSodium = input.nfSodium,
        nfTotalCarbohydrate = input.nfTotalCarbohydrate,
        nfDietaryFiber = input.nfDietaryFiber,
        nfSugars = input.nfSugars,
        nfProtein = input.nfProtein,
        nfVitaminA = input.nfVitaminA,
        nfVitaminB6 = input.nfVitaminB6,
        nfVitaminC = input.nfVitaminC,
        nfVitaminD = input.nfVitaminD,
        nfCalcium = input.nfCalcium,
        nfIron = input.nfIron,
        nfPotassium = input.nfPotassium,
        nfFolate = input.nfFolate,
        fullNutrients = mapEntitiesToDomain(input.fullNutrients),
        isFavorite = false
    )
}

fun mapDomainToEntity(input: Food): FoodEntity {
    return FoodEntity(
        id = input.id,
        name = input.name,
        photo = input.photo,
        servingQuantity = input.servingQuantity,
        servingUnit = input.servingUnit,
        servingWeightGrams = input.servingWeightGrams,
        nfCalories = input.nfCalories,
        nfTotalFat = input.nfTotalFat,
        nfSaturatedFat = input.nfSaturatedFat,
        nfTransFat = input.nfTransFat,
        nfPolyunsaturatedFat = input.nfPolyunsaturatedFat,
        nfMonounsaturatedFat = input.nfMonounsaturatedFat,
        nfCholesterol = input.nfCholesterol,
        nfSodium = input.nfSodium,
        nfTotalCarbohydrate = input.nfTotalCarbohydrate,
        nfDietaryFiber = input.nfDietaryFiber,
        nfSugars = input.nfSugars,
        nfProtein = input.nfProtein,
        nfVitaminA = input.nfVitaminA,
        nfVitaminB6 = input.nfVitaminB6,
        nfVitaminC = input.nfVitaminC,
        nfVitaminD = input.nfVitaminD,
        nfCalcium = input.nfCalcium,
        nfIron = input.nfIron,
        nfPotassium = input.nfPotassium,
        nfFolate = input.nfFolate,
        fullNutrients = mapDomainToEntities(input.fullNutrients)
    )
}

private fun getNutrientValue(nutrients: List<FullNutrientsItem?>?, id: Int): Double {
    return nutrients?.find { it?.attributeId == id }?.value ?: 0.0
}