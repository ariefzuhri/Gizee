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
import com.ariefzuhri.gizee.core.utils.TAG
import com.ariefzuhri.gizee.databinding.FragmentNutritionFactsBinding
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
        viewModel.foods = foods!!
        viewModel.nutrients.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> binding.tvFullNutrients.visibility = View.GONE
                    is Resource.Success -> {
                        val rawNutrients = result.data
                        if (!(foods.isNullOrEmpty() && rawNutrients.isNullOrEmpty())) {
                            populateFullNutrients(foods!!, rawNutrients!!)
                        }
                        binding.tvFullNutrients.visibility = View.VISIBLE
                    }
                    is Resource.Error -> AppUtils.showToast(context, result.message)
                }
            }
        }

        populateChart()
        populateNutritionFacts()
    }

    private fun populateChart() {
        var totalCalories = 0.0f
        var totalCarbohydrates = 0.0f
        var totalFats = 0.0f
        var totalProteins = 0.0f

        if (foods != null) {
            for (food in foods!!) {
                totalCalories += food.nfCalories.toFloat()
                totalCarbohydrates += food.nfTotalCarbohydrate.toFloat()
                totalFats += food.nfTotalFat.toFloat()
                totalProteins += food.nfProtein.toFloat()
            }
        }

        val caloriesFromCarbohydrates = totalCarbohydrates * 4
        val caloriesFromFats = totalFats * 9
        val caloriesFromProteins = totalProteins * 4

        val chartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("Source of Calories")
            .subtitle("Total calories: ${AppUtils.formatToDecimal(totalCalories)} kcal")
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
                    .setTransFat(food.nfTransFat)
                    .setPolyunsaturatedFat(food.nfPolyFat)
                    .setMonounsaturatedFat(food.nfMonoFat)
                    .setCholesterol(food.nfCholesterol)
                    .setSodium(food.nfSodium)
                    .setTotalCarbohydrates(food.nfTotalCarbohydrate)
                    .setDietaryFiber(food.nfDietaryFiber)
                    .setSugars(food.nfSugars)
                    .setProtein(food.nfProtein)
                    .setVitaminA(food.nfVitA)
                    .setVitaminB6(food.nfVitB6)
                    .setVitaminC(food.nfVitC)
                    .setVitaminD(food.nfVitD)
                    .setCalcium(food.nfCalcium)
                    .setIron(food.nfIron)
                    .setPotassium(food.nfPotassium)
                    .setFolate(food.nfFolate)
                    .create()
                binding.nfView.addData(nfData)
            }
        }
        binding.nfView.drawLabel()
    }

    private fun populateFullNutrients(foods: List<Food>, rawNutrients: List<Nutrient>) {
        binding.tvFullNutrients.setOnClickListener {
            FullNutrientsFragment.newInstance(foods, rawNutrients)
                .show(childFragmentManager, FullNutrientsFragment.TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}