@file:Suppress("unused")

package com.ariefzuhri.nutritionfactslabel

class NutritionFactsData(builder: Builder) {

    val servingSize: Double = builder.servingSize
    val calories: Double = builder.calories
    val totalFat: Double = builder.totalFat
    val saturatedFat: Double = builder.saturatedFat
    val transFat: Double = builder.transFat
    val polyunsaturatedFat: Double = builder.polyunsaturatedFat
    val monounsaturatedFat: Double = builder.monounsaturatedFat
    val cholesterol: Double = builder.cholesterol
    val sodium: Double = builder.sodium
    val totalCarbohydrates: Double = builder.totalCarbohydrates
    val dietaryFiber: Double = builder.dietaryFiber
    val sugars: Double = builder.sugars
    val protein: Double = builder.protein
    val vitaminA: Double = builder.vitaminA
    val vitaminB6: Double = builder.vitaminB6
    val vitaminC: Double = builder.vitaminC
    val vitaminD: Double = builder.vitaminD
    val calcium: Double = builder.calcium
    val iron: Double = builder.iron
    val potassium: Double = builder.potassium
    val folate: Double = builder.folate

    class Builder {
        var servingSize: Double = 0.0
        var calories: Double = 0.0
        var totalFat: Double = 0.0
        var saturatedFat: Double = 0.0
        var transFat: Double = 0.0
        var polyunsaturatedFat: Double = 0.0
        var monounsaturatedFat: Double = 0.0
        var cholesterol: Double = 0.0
        var sodium: Double = 0.0
        var totalCarbohydrates: Double = 0.0
        var dietaryFiber: Double = 0.0
        var sugars: Double = 0.0
        var protein: Double = 0.0
        var vitaminA: Double = 0.0
        var vitaminB6: Double = 0.0
        var vitaminC: Double = 0.0
        var vitaminD: Double = 0.0
        var calcium: Double = 0.0
        var iron: Double = 0.0
        var potassium: Double = 0.0
        var folate: Double = 0.0

        fun setServingSize(servingSize: Double?): Builder {
            this.servingSize = servingSize ?: 0.0
            return this
        }

        fun setCalories(calories: Double?): Builder {
            this.calories = calories ?: 0.0
            return this
        }

        fun setTotalFat(totalFat: Double?): Builder {
            this.totalFat = totalFat ?: 0.0
            return this
        }

        fun setSaturatedFat(saturatedFat: Double?): Builder {
            this.saturatedFat = saturatedFat ?: 0.0
            return this
        }

        fun setTransFat(transFat: Double?): Builder {
            this.transFat = transFat ?: 0.0
            return this
        }

        fun setPolyunsaturatedFat(polyunsaturatedFat: Double?): Builder {
            this.polyunsaturatedFat = polyunsaturatedFat ?: 0.0
            return this
        }

        fun setMonounsaturatedFat(monounsaturatedFat: Double?): Builder {
            this.monounsaturatedFat = monounsaturatedFat ?: 0.0
            return this
        }

        fun setCholesterol(cholesterol: Double?): Builder {
            this.cholesterol = cholesterol ?: 0.0
            return this
        }

        fun setSodium(sodium: Double?): Builder {
            this.sodium = sodium ?: 0.0
            return this
        }

        fun setTotalCarbohydrates(totalCarbohydrates: Double?): Builder {
            this.totalCarbohydrates = totalCarbohydrates ?: 0.0
            return this
        }

        fun setDietaryFiber(dietaryFiber: Double?): Builder {
            this.dietaryFiber = dietaryFiber ?: 0.0
            return this
        }

        fun setSugars(sugars: Double?): Builder {
            this.sugars = sugars ?: 0.0
            return this
        }

        fun setProtein(protein: Double?): Builder {
            this.protein = protein ?: 0.0
            return this
        }

        fun setVitaminA(vitaminA: Double?): Builder {
            this.vitaminA = vitaminA ?: 0.0
            return this
        }

        fun setVitaminB6(vitaminB6: Double?): Builder {
            this.vitaminB6 = vitaminB6 ?: 0.0
            return this
        }

        fun setVitaminC(vitaminC: Double?): Builder {
            this.vitaminC = vitaminC ?: 0.0
            return this
        }

        fun setVitaminD(vitaminD: Double?): Builder {
            this.vitaminD = vitaminD ?: 0.0
            return this
        }

        fun setCalcium(calcium: Double?): Builder {
            this.calcium = calcium ?: 0.0
            return this
        }

        fun setIron(iron: Double?): Builder {
            this.iron = iron ?: 0.0
            return this
        }

        fun setPotassium(potassium: Double?): Builder {
            this.potassium = potassium ?: 0.0
            return this
        }

        fun setFolate(folate: Double?): Builder {
            this.folate = folate ?: 0.0
            return this
        }

        fun create(): NutritionFactsData {
            return NutritionFactsData(this)
        }
    }
}