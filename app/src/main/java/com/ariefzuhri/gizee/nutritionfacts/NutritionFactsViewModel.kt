package com.ariefzuhri.gizee.nutritionfacts

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ariefzuhri.gizee.core.data.repository.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.domain.usecase.FoodUseCase
import com.ariefzuhri.gizee.core.utils.FONT_SEN
import com.ariefzuhri.gizee.core.utils.TOTAL_CALORIES_CARBOHYDRATE
import com.ariefzuhri.gizee.core.utils.TOTAL_CALORIES_FAT
import com.ariefzuhri.gizee.core.utils.TOTAL_CALORIES_PROTEIN
import com.ariefzuhri.nutritionfactslabel.model.NutritionFactsData
import com.ariefzuhri.nutritionfactslabel.model.NutritionFactsDataSet
import com.ariefzuhri.nutritionfactslabel.model.NutritionFactsEntry

class NutritionFactsViewModel(foodUseCase: FoodUseCase) : ViewModel() {

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
                NutritionFactsEntry.Builder()
                    .setServingSize(it.servingWeightGrams)
                    .setCalories(it.nfCalories)
                    .setTotalFat(it.nfTotalFat)
                    .setSaturatedFat(it.nfSaturatedFat)
                    .setTransFat(it.nfTransFat)
                    .setPolyunsaturatedFat(it.nfPolyFat)
                    .setMonounsaturatedFat(it.nfMonoFat)
                    .setCholesterol(it.nfCholesterol)
                    .setSodium(it.nfSodium)
                    .setTotalCarbohydrates(it.nfTotalCarbohydrate)
                    .setDietaryFiber(it.nfDietaryFiber)
                    .setSugars(it.nfSugars)
                    .setProtein(it.nfProtein)
                    .setVitaminA(it.nfVitA)
                    .setVitaminB6(it.nfVitB6)
                    .setVitaminC(it.nfVitC)
                    .setVitaminD(it.nfVitD)
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
        foodUseCase.getNutrients().asLiveData()
}