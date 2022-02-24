package com.ariefzuhri.gizee.feature.nutritionfacts.presentation

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.feature.nutritionfacts.domain.usecase.NutritionFactsUseCase
import com.ariefzuhri.gizee.core.common.dto.Resource
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient
import com.ariefzuhri.gizee.core.common.util.FONT_SEN
import com.ariefzuhri.gizee.core.common.util.TOTAL_CALORIES_CARBOHYDRATE
import com.ariefzuhri.gizee.core.common.util.TOTAL_CALORIES_FAT
import com.ariefzuhri.gizee.core.common.util.TOTAL_CALORIES_PROTEIN
import com.ariefzuhri.gizee.library.nutritionfactslabel.model.NutritionFactsData
import com.ariefzuhri.gizee.library.nutritionfactslabel.model.NutritionFactsDataSet

class NutritionFactsViewModel(useCase: NutritionFactsUseCase) : ViewModel() {

    var totalCalories = 0.0f
    var caloriesFromCarbohydrates = 0.0f
    var caloriesFromFats = 0.0f
    var caloriesFromProteins = 0.0f
    var nfData = NutritionFactsData()

    var foods: List<Food>? = null
        set(value) {
            field = value

            var totalCalories = 0.0f
            var totalCarbohydrates = 0.0f
            var totalFats = 0.0f
            var totalProteins = 0.0f

            val nfEntries = value?.map {
                // Source of calories chart
                totalCalories += it.nfCalories.toFloat()
                totalCarbohydrates += it.nfTotalCarbohydrate.toFloat()
                totalFats += it.nfTotalFat.toFloat()
                totalProteins += it.nfProtein.toFloat()

                // Nutrition facts label
                com.ariefzuhri.gizee.library.nutritionfactslabel.model.NutritionFactsEntry.Builder()
                    .setServingSize(it.servingWeightGrams)
                    .setCalories(it.nfCalories)
                    .setTotalFat(it.nfTotalFat)
                    .setSaturatedFat(it.nfSaturatedFat)
                    .setTransFat(it.nfTransFat)
                    .setPolyunsaturatedFat(it.nfPolyunsaturatedFat)
                    .setMonounsaturatedFat(it.nfMonounsaturatedFat)
                    .setCholesterol(it.nfCholesterol)
                    .setSodium(it.nfSodium)
                    .setTotalCarbohydrates(it.nfTotalCarbohydrate)
                    .setDietaryFiber(it.nfDietaryFiber)
                    .setSugars(it.nfSugars)
                    .setProtein(it.nfProtein)
                    .setVitaminA(it.nfVitaminA)
                    .setVitaminB6(it.nfVitaminB6)
                    .setVitaminC(it.nfVitaminC)
                    .setVitaminD(it.nfVitaminD)
                    .setCalcium(it.nfCalcium)
                    .setIron(it.nfIron)
                    .setPotassium(it.nfPotassium)
                    .setFolate(it.nfFolate)
                    .create()
            } ?: listOf()

            val nfDataSet = NutritionFactsDataSet(nfEntries)
            nfData = NutritionFactsData(nfDataSet, FONT_SEN.toUri())

            this.totalCalories = totalCalories
            this.caloriesFromCarbohydrates = totalCarbohydrates * TOTAL_CALORIES_CARBOHYDRATE
            this.caloriesFromFats = totalFats * TOTAL_CALORIES_FAT
            this.caloriesFromProteins = totalProteins * TOTAL_CALORIES_PROTEIN
        }

    val nutrients: LiveData<Resource<List<Nutrient>>> =
        useCase.getNutrients().asLiveData()
}