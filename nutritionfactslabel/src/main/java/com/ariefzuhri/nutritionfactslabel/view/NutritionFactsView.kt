package com.ariefzuhri.nutritionfactslabel.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.ariefzuhri.nutritionfactslabel.utils.calculatePercentage
import com.ariefzuhri.nutritionfactslabel.model.NutritionFactsData
import com.ariefzuhri.nutritionfactslabel.utils.pxToDp
import com.ariefzuhri.nutritionfactslabel.utils.toDecimal

// Source: https://www.fda.gov/food/new-nutrition-facts-label/daily-value-new-nutrition-and-supplement-facts-labels
private const val TOTAL_CALORIES_FAT = 9 // kcal/g
private const val DAILY_VALUE_FAT = 78.0 // g
private const val DAILY_VALUE_SATURATED_FAT = 20.0 // g
private const val DAILY_VALUE_CHOLESTEROL = 300.0 // mg
private const val DAILY_VALUE_SODIUM = 2300.0 // mg
private const val DAILY_VALUE_CARBOHYDRATE = 275.0 // g
private const val DAILY_VALUE_DIETARY_FIBER = 28.0 // g
private const val DAILY_VALUE_VITAMIN_A = 900.0 // mcg RAE
private const val DAILY_VALUE_VITAMIN_B6 = 1.7 // mg
private const val DAILY_VALUE_VITAMIN_C = 90.0 // mg
private const val DAILY_VALUE_VITAMIN_D = 20.0 // mcg
private const val DAILY_VALUE_CALCIUM = 1300.0 // mg
private const val DAILY_VALUE_IRON = 18.0 // mg
private const val DAILY_VALUE_POTASSIUM = 4700.0 // mg
private const val DAILY_VALUE_FOLATE = 400.0 // mcg DFE

private const val STYLES_CSS_FILE = "nutrition_facts_label.css"

class NutritionFactsView(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {

    var data = NutritionFactsData()

    fun drawLabel() {
        val mainContent = createMainContent()
        val typefaceStyle = createTypefaceStyle()
        val htmlString = """
            <html>
                <head>
                    <link href="$STYLES_CSS_FILE" type="text/css" rel="stylesheet"/>
                    $typefaceStyle
                </head>
                <body>
                    $mainContent
                </body>
            </html>
        """.trimIndent()

        loadDataWithBaseURL(
            "file:///android_asset/",
            htmlString,
            "text/html",
            "UTF-8",
            null
        )
    }

    private fun createMainContent(): String {
        var servingSize = 0.0
        var calories = 0.0
        var totalFat = 0.0
        var saturatedFat = 0.0
        var transFat = 0.0
        var polyunsaturatedFat = 0.0
        var monounsaturatedFat = 0.0
        var cholesterol = 0.0
        var sodium = 0.0
        var totalCarbohydrates = 0.0
        var dietaryFiber = 0.0
        var sugars = 0.0
        var protein = 0.0
        var vitaminA = 0.0
        var vitaminB6 = 0.0
        var vitaminC = 0.0
        var vitaminD = 0.0
        var calcium = 0.0
        var iron = 0.0
        var potassium = 0.0
        var folate = 0.0

        data.dataSet.entries.forEach {
            servingSize += it.servingSize
            calories += it.calories
            totalFat += it.totalFat
            saturatedFat += it.saturatedFat
            transFat += it.transFat
            polyunsaturatedFat += it.polyunsaturatedFat
            monounsaturatedFat += it.monounsaturatedFat
            cholesterol += it.cholesterol
            sodium += it.sodium
            totalCarbohydrates += it.totalCarbohydrates
            dietaryFiber += it.dietaryFiber
            sugars += it.sugars
            protein += it.protein
            vitaminA += it.vitaminA
            vitaminB6 += it.vitaminB6
            vitaminC += it.vitaminC
            vitaminD += it.vitaminD
            calcium += it.calcium
            iron += it.iron
            potassium += it.potassium
            folate += it.folate
        }

        val webViewWidth = pxToDp(measuredWidth)
        // Source: http://jsfiddle.net/thL6j/
        @Suppress("Reformat")
        return """
            <div id="nutritionfacts" style="width:${webViewWidth - 24}">
                <table width="${webViewWidth - 24}" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <td align="center" class="header">Nutrition Facts</td>
                        </tr>
                        <tr>
                            <td>
                                <div class="serving">Per <span class="highlighted">${servingSize.toDecimal()} g</span> Serving Size</div>
                            </td>
                        </tr>
                        <tr style="height: 8px">
                            <td bgcolor="#000000"></td>
                        </tr>
                        <tr>
                            <td style="font-size: 12px">
                                <div class="line">Amount Per Serving</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Calories <div class="weight">${calories.toDecimal()}</div>
                                    </div>
                                    <div style="padding-top: 1px; float: right;" class="labellight">Calories from Fat <div
                                            class="weight">${(totalFat * TOTAL_CALORIES_FAT).toDecimal()}</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="dvlabel">% Daily Value<sup>*</sup></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Total Fat <div class="weight">${totalFat.toDecimal()} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(totalFat, DAILY_VALUE_FAT)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Saturated Fat <div class="weight">${saturatedFat.toDecimal()} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(saturatedFat, DAILY_VALUE_SATURATED_FAT)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight"><i>Trans</i> Fat <div class="weight">${transFat.toDecimal()} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Polyunsaturated Fat <div class="weight">${polyunsaturatedFat.toDecimal()} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Monounsaturated Fat <div class="weight">${monounsaturatedFat.toDecimal()} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Cholesterol <div class="weight">${cholesterol.toDecimal()} mg</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(cholesterol, DAILY_VALUE_CHOLESTEROL)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Sodium <div class="weight">${sodium.toDecimal()} mg</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(sodium, DAILY_VALUE_SODIUM)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Total Carbohydrates <div class="weight">${totalCarbohydrates.toDecimal()} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(totalCarbohydrates, DAILY_VALUE_CARBOHYDRATE)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Dietary Fiber <div class="weight">${dietaryFiber.toDecimal()} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(dietaryFiber, DAILY_VALUE_DIETARY_FIBER)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Sugars <div class="weight">${sugars.toDecimal()} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Protein <div class="weight">${protein.toDecimal()} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr style="height: 8px">
                            <td bgcolor="#000000"></td>
                        </tr>
                        <tr>
                            <td>
                                <table cellspacing="0" cellpadding="0" border="0" class="vitamins">
                                    <tbody>
                                        <tr>
                                            <td>Vitamin A &nbsp;&nbsp; ${calculatePercentage(vitaminA, DAILY_VALUE_VITAMIN_A)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Calcium &nbsp;&nbsp; ${calculatePercentage(calcium, DAILY_VALUE_CALCIUM)}</td>
                                        </tr>
                                        <tr>
                                            <td>Vitamin B6 &nbsp;&nbsp; ${calculatePercentage(vitaminB6, DAILY_VALUE_VITAMIN_B6)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Iron &nbsp;&nbsp; ${calculatePercentage(iron, DAILY_VALUE_IRON)}</td>
                                        </tr>
                                        <tr>
                                            <td>Vitamin C &nbsp;&nbsp; ${calculatePercentage(vitaminC, DAILY_VALUE_VITAMIN_C)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Potassium &nbsp;&nbsp; ${calculatePercentage(potassium, DAILY_VALUE_POTASSIUM)}</td>
                                        </tr>
                                        <tr>
                                            <td>Vitamin D &nbsp;&nbsp; ${calculatePercentage(vitaminD, DAILY_VALUE_VITAMIN_D)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Folate &nbsp;&nbsp; ${calculatePercentage(folate, DAILY_VALUE_FOLATE)}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="labellight">* Based on a regular <span style="color:#63BF61">2000 calorie diet</span>
                                        <br><br><i>Nutritional details are an estimate and should only be used as a guide for
                                            approximation.</i>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        """.trimIndent()
    }

    private fun createTypefaceStyle(): String {
        return """
            <style type="text/css">
                @font-face {
                  font-family: custom_font;
                  src: url(${data.typeface})
                }
    
                body {
                  font-family: custom_font;
                  font-size: 14px;
                }
            </style>
        """.trimIndent()
    }
}