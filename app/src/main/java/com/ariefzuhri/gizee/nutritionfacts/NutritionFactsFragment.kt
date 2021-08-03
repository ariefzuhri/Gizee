package com.ariefzuhri.gizee.nutritionfacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel.NutritionFactsData
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.AppUtils.TAG
import com.ariefzuhri.gizee.databinding.FragmentNutritionFactsBinding
import com.ariefzuhri.gizee.fullnutrients.FullNutrientsFragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_FOODS = "foods"

class NutritionFactsFragment : Fragment() {

    private var _binding: FragmentNutritionFactsBinding? = null
    private val binding get() = _binding!!

    private var foods: List<Food>? = null
    private var rawNutrients: List<Nutrient>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            foods = it.getParcelableArrayList(ARG_FOODS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutritionFactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(foods: List<Food?>?) =
            NutritionFactsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FOODS, foods as ArrayList<Food?>?)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: NutritionFactsViewModel by viewModel()
        viewModel.getNutrients.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        rawNutrients = result.data
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    is Resource.Error -> AppUtils.showToast(context, result.message)
                }
            }
        }

        populateChart()
        populateNutritionFacts()
        populateFullNutrients()
    }

    private fun populateChart() {
        var totalCalories = 0.0f
        var totalCarbohydrates = 0.0f
        var totalFats = 0.0f
        var totalProteins = 0.0f

        if (foods != null) {
            for (food in foods!!) {
                totalCalories += food.nfCalories!!.toFloat()
                totalCarbohydrates += food.nfTotalCarbohydrate!!.toFloat()
                totalFats += food.nfTotalFat!!.toFloat()
                totalProteins += food.nfProtein!!.toFloat()
            }
        }

        val caloriesFromCarbohydrates = totalCarbohydrates * 4
        val caloriesFromFats = totalFats * 9
        val caloriesFromProteins = totalProteins * 4

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
        binding.chartCalories.aa_drawChartWithChartModel(chartModel)
    }

    private fun populateNutritionFacts() {
        if (foods != null) {
            for (food in foods!!) {
                val nfData = NutritionFactsData.Builder()
                    .setServingSize(food.servingWeightGrams)
                    .setCalories(food.nfCalories)
                    .setTotalFat(food.nfTotalFat)
                    .setSaturatedFat(food.nfSaturatedFat)
                    .setTransFat(getNutrientValueById(food.fullNutrients, 605))
                    .setPolyunsaturatedFat(getNutrientValueById(food.fullNutrients, 646))
                    .setMonounsaturatedFat(getNutrientValueById(food.fullNutrients, 645))
                    .setCholesterol(food.nfCholesterol)
                    .setSodium(food.nfSodium)
                    .setTotalCarbohydrates(food.nfTotalCarbohydrate)
                    .setDietaryFiber(food.nfDietaryFiber)
                    .setSugars(food.nfSugars)
                    .setProtein(food.nfProtein)
                    .setVitaminA(getNutrientValueById(food.fullNutrients, 320))
                    .setVitaminB6(getNutrientValueById(food.fullNutrients, 415))
                    .setVitaminC(getNutrientValueById(food.fullNutrients, 401))
                    .setVitaminD(getNutrientValueById(food.fullNutrients, 328))
                    .setCalcium(getNutrientValueById(food.fullNutrients, 301))
                    .setIron(getNutrientValueById(food.fullNutrients, 303))
                    .setPotassium(food.nfPotassium)
                    .setFolate(getNutrientValueById(food.fullNutrients, 435))
                    .create()
                binding.nfView.addData(nfData)
            }
        }
        binding.nfView.drawLabel()
    }

    private fun getNutrientValueById(nutrients: List<Nutrient?>?, id: Int): Double {
        if (nutrients != null) for (nutrient in nutrients) if (nutrient?.id == id) return nutrient.value
            ?: 0.0
        return 0.0
    }

    private fun populateFullNutrients() {
        binding.tvFullNutrients.setOnClickListener {
            if (rawNutrients != null) {
                FullNutrientsFragment.newInstance(foods!!, rawNutrients!!)
                    .show(childFragmentManager, FullNutrientsFragment.TAG)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}