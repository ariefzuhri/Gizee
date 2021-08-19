@file:Suppress("unused")

package com.ariefzuhri.nutritionfactslabel

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.webkit.WebView

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

    private var data = NutritionFactsData.Builder().create()
    private var typeFace = ""

    fun addData(newData: NutritionFactsData) {
        data = NutritionFactsData.Builder()
            .setServingSize(data.servingSize.plus(newData.servingSize))
            .setCalories(data.calories.plus(newData.calories))
            .setTotalFat(data.totalFat.plus(newData.totalFat))
            .setSaturatedFat(data.saturatedFat.plus(newData.saturatedFat))
            .setTransFat(data.transFat.plus(newData.transFat))
            .setPolyunsaturatedFat(data.polyunsaturatedFat.plus(newData.polyunsaturatedFat))
            .setMonounsaturatedFat(data.monounsaturatedFat.plus(newData.monounsaturatedFat))
            .setCholesterol(data.cholesterol.plus(newData.cholesterol))
            .setSodium(data.sodium.plus(newData.sodium))
            .setTotalCarbohydrates(data.totalCarbohydrates.plus(newData.totalCarbohydrates))
            .setDietaryFiber(data.dietaryFiber.plus(newData.dietaryFiber))
            .setSugars(data.sugars.plus(newData.sugars))
            .setProtein(data.protein.plus(newData.protein))
            .setVitaminA(data.vitaminA.plus(newData.vitaminA))
            .setVitaminB6(data.vitaminB6.plus(newData.vitaminB6))
            .setVitaminC(data.vitaminC.plus(newData.vitaminC))
            .setVitaminD(data.vitaminD.plus(newData.vitaminD))
            .setCalcium(data.calcium.plus(newData.calcium))
            .setIron(data.iron.plus(newData.iron))
            .setPotassium(data.potassium.plus(newData.potassium))
            .setFolate(data.folate.plus(newData.folate))
            .create()
    }

    fun clearData() {
        data = NutritionFactsData.Builder().create()
    }

    fun setTypeface(path: Uri) {
        typeFace = """
            <style type="text/css">
                @font-face {
                  font-family: custom_font;
                  src: url($path)
                }
    
                body {
                  font-family: custom_font;
                  font-size: 14px;
                }
            </style>
        """.trimIndent()
    }

    // Source: http://jsfiddle.net/thL6j/
    fun drawLabel() {
        val webViewWidth = pxToDp(measuredWidth)
        @Suppress("Reformat") val main = """
            <div id="nutritionfacts" style="width:${webViewWidth - 24}">
                <table width="${webViewWidth - 24}" cellspacing="0" cellpadding="0">
                    <tbody>
                        <tr>
                            <td align="center" class="header">Nutrition Facts</td>
                        </tr>
                        <tr>
                            <td>
                                <div class="serving">Per <span class="highlighted">${formatToDecimal(data.servingSize)} g</span> Serving Size</div>
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
                                    <div class="label">Calories <div class="weight">${formatToDecimal(data.calories)}</div>
                                    </div>
                                    <div style="padding-top: 1px; float: right;" class="labellight">Calories from Fat <div
                                            class="weight">${formatToDecimal(data.totalFat * TOTAL_CALORIES_FAT)}</div>
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
                                    <div class="label">Total Fat <div class="weight">${formatToDecimal(data.totalFat)} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(data.totalFat, DAILY_VALUE_FAT)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Saturated Fat <div class="weight">${formatToDecimal(data.saturatedFat)} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(data.saturatedFat, DAILY_VALUE_SATURATED_FAT)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight"><i>Trans</i> Fat <div class="weight">${formatToDecimal(data.transFat)} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Polyunsaturated Fat <div class="weight">${formatToDecimal(data.polyunsaturatedFat)} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Monounsaturated Fat <div class="weight">${formatToDecimal(data.monounsaturatedFat)} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Cholesterol <div class="weight">${formatToDecimal(data.cholesterol)} mg</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(data.cholesterol, DAILY_VALUE_CHOLESTEROL)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Sodium <div class="weight">${formatToDecimal(data.sodium)} mg</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(data.sodium, DAILY_VALUE_SODIUM)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Total Carbohydrates <div class="weight">${formatToDecimal(data.totalCarbohydrates)} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(data.totalCarbohydrates, DAILY_VALUE_CARBOHYDRATE)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Dietary Fiber <div class="weight">${formatToDecimal(data.dietaryFiber)} g</div>
                                    </div>
                                    <div class="dv">${calculatePercentage(data.dietaryFiber, DAILY_VALUE_DIETARY_FIBER)}</div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="indent">
                                <div class="line">
                                    <div class="labellight">Sugars <div class="weight">${formatToDecimal(data.sugars)} g</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="line">
                                    <div class="label">Protein <div class="weight">${formatToDecimal(data.protein)} g</div>
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
                                            <td>Vitamin A &nbsp;&nbsp; ${calculatePercentage(data.vitaminA, DAILY_VALUE_VITAMIN_A)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Calcium &nbsp;&nbsp; ${calculatePercentage(data.calcium, DAILY_VALUE_CALCIUM)}</td>
                                        </tr>
                                        <tr>
                                            <td>Vitamin B6 &nbsp;&nbsp; ${calculatePercentage(data.vitaminB6, DAILY_VALUE_VITAMIN_B6)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Iron &nbsp;&nbsp; ${calculatePercentage(data.iron, DAILY_VALUE_IRON)}</td>
                                        </tr>
                                        <tr>
                                            <td>Vitamin C &nbsp;&nbsp; ${calculatePercentage(data.vitaminC, DAILY_VALUE_VITAMIN_C)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Potassium &nbsp;&nbsp; ${calculatePercentage(data.potassium, DAILY_VALUE_POTASSIUM)}</td>
                                        </tr>
                                        <tr>
                                            <td>Vitamin D &nbsp;&nbsp; ${calculatePercentage(data.vitaminD, DAILY_VALUE_VITAMIN_D)}</td>
                                            <td align="center">•</td>
                                            <td align="right">Folate &nbsp;&nbsp; ${calculatePercentage(data.folate, DAILY_VALUE_FOLATE)}</td>
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

        val data = """
            <html>
                <head>
                    <link href="$STYLES_CSS_FILE" type="text/css" rel="stylesheet"/>
                    $typeFace
                </head>
                <body>
                    $main
                </body>
            </html>
        """.trimIndent()

        loadDataWithBaseURL(
            "file:///android_asset/",
            data,
            "text/html",
            "UTF-8",
            null
        )
    }
}