package com.ariefzuhri.nutritionfactslabel.model

import android.net.Uri
import androidx.core.net.toUri

data class NutritionFactsData(
    var dataSet: NutritionFactsDataSet = NutritionFactsDataSet(),
    var typeface: Uri = "".toUri(),
)