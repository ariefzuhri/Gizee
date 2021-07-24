package com.ariefzuhri.gizee.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel.NutritionFactsData
import com.ariefzuhri.gizee.databinding.ActivityDetailsBinding
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.Constants.Companion.EXTRA_FOOD
import com.ariefzuhri.gizee.core.ui.viewmodel.ViewModelFactory
import com.ariefzuhri.gizee.fullnutrients.FullNutrientsFragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import org.apache.commons.lang3.StringUtils

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var food: FoodEntity
    private var rawNutrients: List<NutrientEntity>? = null
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(EXTRA_FOOD)) {
            food = intent.getParcelableExtra(EXTRA_FOOD)!!
        } else onBackPressed()

        initializeToolbar()

        AppUtils.loadImage(this, binding.imgPhoto, food.photo)
        binding.tvTitle.text = StringUtils.capitalize(food.name)
        binding.tvCalories.text =
            resources.getString(R.string.calories, AppUtils.getDecimalFormat(food.nfCalories))
        binding.tvMeasure.text = resources.getString(
            R.string.measure,
            AppUtils.getDecimalFormat(food.servingQty),
            food.servingUnit
        )
        binding.tvWeight.text = resources.getString(
            R.string.weight,
            AppUtils.getDecimalFormat(food.servingWeightGrams),
            "g"
        )

        populateChart(food)
        populateNutritionFacts(food)
        populateFullNutrients(food)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]
        viewModel.getNutrients().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Success -> rawNutrients = result.data
                    is Resource.Error -> AppUtils.showToast(this, result.message)
                    is Resource.Loading -> Log.d(javaClass.simpleName, "Loading data")
                }
            }
        }
        viewModel.isFavorite(food.id).observe(this) { result ->
            food.isFavorite = result != null
            setFavoriteIcon(food.isFavorite!!)
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        val menuFavorite = binding.toolbar.menu[0]
        if (isFavorite) menuFavorite.setIcon(R.drawable.ic_bookmark)
        else menuFavorite.setIcon(R.drawable.ic_bookmark_outline)
    }

    private fun initializeToolbar() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_favorite) {
                val newState = !food.isFavorite!!
                viewModel.setFavorite(food, newState)
            }
            true
        }
    }

    private fun populateChart(food: FoodEntity) {
        val totalCalories = food.nfCalories!!.toFloat()
        val caloriesFromCarbohydrates = food.nfTotalCarbohydrate!!.toFloat() * 4
        val caloriesFromFats = food.nfTotalFat!!.toFloat() * 9
        val caloriesFromProteins = food.nfProtein!!.toFloat() * 4

        val nfBinding = binding.layoutNutritionFacts
        val chartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("Source of Calories")
            .subtitle("Total calories: ${AppUtils.getDecimalFormat(totalCalories)} kcal")
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#61AAEE", "#EBEE61", "#64EE61"))
            .series(
                arrayOf(
                    AASeriesElement()
                        .dataLabels(AADataLabels().format("<b>{point.name}</b>: {point.percentage:.1f}%"))
                        .name("Total calories (kcal)")
                        .data(
                            arrayOf(
                                arrayOf("Carbohydrates", caloriesFromCarbohydrates),
                                arrayOf("Fats", caloriesFromFats),
                                arrayOf("Proteins", caloriesFromProteins),
                            )
                        )
                )
            )
        nfBinding.chartCalories.aa_drawChartWithChartModel(chartModel)
    }

    private fun populateNutritionFacts(food: FoodEntity) {
        val nfData = NutritionFactsData.Builder()
            .setServingSize(food.servingWeightGrams)
            .setCalories(food.nfCalories)
            .setTotalFat(food.nfTotalFat)
            .setSaturatedFat(food.nfSaturatedFat)
            .setTransFat(AppUtils.getNutrientValueById(food.fullNutrients, 605))
            .setPolyunsaturatedFat(AppUtils.getNutrientValueById(food.fullNutrients, 646))
            .setMonounsaturatedFat(AppUtils.getNutrientValueById(food.fullNutrients, 645))
            .setCholesterol(food.nfCholesterol)
            .setSodium(food.nfSodium)
            .setTotalCarbohydrates(food.nfTotalCarbohydrate)
            .setDietaryFiber(food.nfDietaryFiber)
            .setSugars(food.nfSugars)
            .setProtein(food.nfProtein)
            .setVitaminA(AppUtils.getNutrientValueById(food.fullNutrients, 320))
            .setVitaminB6(AppUtils.getNutrientValueById(food.fullNutrients, 415))
            .setVitaminC(AppUtils.getNutrientValueById(food.fullNutrients, 401))
            .setVitaminD(AppUtils.getNutrientValueById(food.fullNutrients, 328))
            .setCalcium(AppUtils.getNutrientValueById(food.fullNutrients, 301))
            .setIron(AppUtils.getNutrientValueById(food.fullNutrients, 303))
            .setPotassium(food.nfPotassium)
            .setFolate(AppUtils.getNutrientValueById(food.fullNutrients, 435))
            .create()
        binding.layoutNutritionFacts.nfView.addData(nfData)
        binding.layoutNutritionFacts.nfView.drawLabel()
    }

    private fun populateFullNutrients(food: FoodEntity) {
        binding.layoutNutritionFacts.tvFullNutrients.setOnClickListener {
            if (rawNutrients != null) {
                FullNutrientsFragment.newInstance(mutableListOf(food), rawNutrients!!)
                    .show(supportFragmentManager, FullNutrientsFragment.TAG)
            }
        }
    }
}