@file:Suppress("unused")

package com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel.MathUtils.calculatePercentage
import com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel.MathUtils.formatToDecimal

// Source: https://www.fda.gov/food/new-nutrition-facts-label/daily-value-new-nutrition-and-supplement-facts-labels
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

class NutritionFactsView(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {

    private var data = NutritionFactsData.Builder().create()

    fun addData(newData: NutritionFactsData) {
        this.data = NutritionFactsData.Builder()
            .setServingSize(this.data.servingSize.plus(newData.servingSize))
            .setCalories(this.data.calories.plus(newData.calories))
            .setTotalFat(this.data.totalFat.plus(newData.totalFat))
            .setSaturatedFat(this.data.saturatedFat.plus(newData.saturatedFat))
            .setTransFat(this.data.transFat.plus(newData.transFat))
            .setPolyunsaturatedFat(this.data.polyunsaturatedFat.plus(newData.polyunsaturatedFat))
            .setMonounsaturatedFat(this.data.monounsaturatedFat.plus(newData.monounsaturatedFat))
            .setCholesterol(this.data.cholesterol.plus(newData.cholesterol))
            .setSodium(this.data.sodium.plus(newData.sodium))
            .setTotalCarbohydrates(this.data.totalCarbohydrates.plus(newData.totalCarbohydrates))
            .setDietaryFiber(this.data.dietaryFiber.plus(newData.dietaryFiber))
            .setSugars(this.data.sugars.plus(newData.sugars))
            .setProtein(this.data.protein.plus(newData.protein))
            .setVitaminA(this.data.vitaminA.plus(newData.vitaminA))
            .setVitaminB6(this.data.vitaminB6.plus(newData.vitaminB6))
            .setVitaminC(this.data.vitaminC.plus(newData.vitaminC))
            .setVitaminD(this.data.vitaminD.plus(newData.vitaminD))
            .setCalcium(this.data.calcium.plus(newData.calcium))
            .setIron(this.data.iron.plus(newData.iron))
            .setPotassium(this.data.potassium.plus(newData.potassium))
            .setFolate(this.data.folate.plus(newData.folate))
            .create()
    }

    fun clearData() {
        this.data = NutritionFactsData.Builder().create()
    }

    // Source: http://jsfiddle.net/thL6j/
    fun drawLabel() {
        val webViewWidth = DimensionUtils.pxToDp(measuredWidth)
        @Suppress("Reformat") val main = "" +
                "<div id=\"nutritionfacts\" style=\"width:${webViewWidth - 24}\">\n" +
                "    <table width=\"${webViewWidth - 24}\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "        <tbody>\n" +
                "            <tr>\n" +
                "                <td align=\"center\" class=\"header\">Nutrition Facts</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"serving\">Per <span class=\"highlighted\">${formatToDecimal(data.servingSize)} g</span> Serving Size</div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr style=\"height: 8px\">\n" +
                "                <td bgcolor=\"#000000\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"font-size: 12px\">\n" +
                "                    <div class=\"line\">Amount Per Serving</div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"label\">Calories <div class=\"weight\">${formatToDecimal(data.calories)}</div>\n" +
                "                        </div>\n" +
                "                        <div style=\"padding-top: 1px; float: right;\" class=\"labellight\">Calories from Fat <div\n" +
                "                                class=\"weight\">${formatToDecimal(data.totalFat * 9)}</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"dvlabel\">% Daily Value<sup>*</sup></div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"label\">Total Fat <div class=\"weight\">${formatToDecimal(data.totalFat)} g</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"dv\">${calculatePercentage(data.totalFat, DAILY_VALUE_FAT)}</div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td class=\"indent\">\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"labellight\">Saturated Fat <div class=\"weight\">${formatToDecimal(data.saturatedFat)} g</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"dv\">${calculatePercentage(data.saturatedFat, DAILY_VALUE_SATURATED_FAT)}</div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td class=\"indent\">\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"labellight\"><i>Trans</i> Fat <div class=\"weight\">${formatToDecimal(data.transFat)} g</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td class=\"indent\">\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"labellight\">Polyunsaturated Fat <div class=\"weight\">${formatToDecimal(data.polyunsaturatedFat)} g</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td class=\"indent\">\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"labellight\">Monounsaturated Fat <div class=\"weight\">${formatToDecimal(data.monounsaturatedFat)} g</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"label\">Cholesterol <div class=\"weight\">${formatToDecimal(data.cholesterol)} mg</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"dv\">${calculatePercentage(data.cholesterol, DAILY_VALUE_CHOLESTEROL)}</div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"label\">Sodium <div class=\"weight\">${formatToDecimal(data.sodium)} mg</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"dv\">${calculatePercentage(data.sodium, DAILY_VALUE_SODIUM)}</div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"label\">Total Carbohydrates <div class=\"weight\">${formatToDecimal(data.totalCarbohydrates)} g</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"dv\">${calculatePercentage(data.totalCarbohydrates, DAILY_VALUE_CARBOHYDRATE)}</div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td class=\"indent\">\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"labellight\">Dietary Fiber <div class=\"weight\">${formatToDecimal(data.dietaryFiber)} g</div>\n" +
                "                        </div>\n" +
                "                        <div class=\"dv\">${calculatePercentage(data.dietaryFiber, DAILY_VALUE_DIETARY_FIBER)}</div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td class=\"indent\">\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"labellight\">Sugars <div class=\"weight\">${formatToDecimal(data.sugars)} g</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"label\">Protein <div class=\"weight\">${formatToDecimal(data.protein)} g</div>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr style=\"height: 8px\">\n" +
                "                <td bgcolor=\"#000000\"></td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"vitamins\">\n" +
                "                        <tbody>\n" +
                "                            <tr>\n" +
                "                                <td>Vitamin A &nbsp;&nbsp; ${calculatePercentage(data.vitaminA, DAILY_VALUE_VITAMIN_A)}</td>\n" +
                "                                <td align=\"center\">•</td>\n" +
                "                                <td align=\"right\">Calcium &nbsp;&nbsp; ${calculatePercentage(data.calcium, DAILY_VALUE_CALCIUM)}</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td>Vitamin B6 &nbsp;&nbsp; ${calculatePercentage(data.vitaminB6, DAILY_VALUE_VITAMIN_B6)}</td>\n" +
                "                                <td align=\"center\">•</td>\n" +
                "                                <td align=\"right\">Iron &nbsp;&nbsp; ${calculatePercentage(data.iron, DAILY_VALUE_IRON)}</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td>Vitamin C &nbsp;&nbsp; ${calculatePercentage(data.vitaminC, DAILY_VALUE_VITAMIN_C)}</td>\n" +
                "                                <td align=\"center\">•</td>\n" +
                "                                <td align=\"right\">Potassium &nbsp;&nbsp; ${calculatePercentage(data.potassium, DAILY_VALUE_POTASSIUM)}</td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td>Vitamin D &nbsp;&nbsp; ${calculatePercentage(data.vitaminD, DAILY_VALUE_VITAMIN_D)}</td>\n" +
                "                                <td align=\"center\">•</td>\n" +
                "                                <td align=\"right\">Folate &nbsp;&nbsp; ${calculatePercentage(data.folate, DAILY_VALUE_FOLATE)}</td>\n" +
                "                            </tr>\n" +
                "                        </tbody>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div class=\"line\">\n" +
                "                        <div class=\"labellight\">* Based on a regular <span style=\"color:#63BF61\">2000 calorie diet</span>\n" +
                "                            <br><br><i>Nutritional details are an estimate and should only be used as a guide for\n" +
                "                                approximation.</i>\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "</div>"

        val head =
            "<html><head><link href=\"styles.css\" type=\"text/css\" rel=\"stylesheet\"/></head>"
        val body = "<body>$main</body></html>"

        loadDataWithBaseURL(
            "file:///android_asset/",
            head + body,
            "text/html",
            "UTF-8",
            null
        )
    }
}