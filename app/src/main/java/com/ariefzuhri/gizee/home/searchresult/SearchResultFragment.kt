package com.ariefzuhri.gizee.home.searchresult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.Nutrient
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiResponse
import com.ariefzuhri.gizee.core.ui.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.ui.customview.nutritionfactslabel.NutritionFactsData
import com.ariefzuhri.gizee.core.ui.viewmodel.ViewModelFactory
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.DataMapper
import com.ariefzuhri.gizee.core.utils.ShimmerHelper
import com.ariefzuhri.gizee.databinding.FragmentSearchResultBinding
import com.ariefzuhri.gizee.fullnutrients.FullNutrientsFragment
import com.ariefzuhri.gizee.home.HomeViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels

private const val ARG_QUERY = "query"

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private var nutrientsData: List<NutrientEntity>? = null
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG: String = SearchResultFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(query: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        val shimmer = ShimmerHelper(binding.shimmer, binding.layoutResult, binding.layoutEmpty)
        shimmer.show()

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
        viewModel.insertHistory(HistoryEntity(query))
        viewModel.searchFoodsByNaturalLanguage(query).observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ApiResponse.Success -> {
                        val foods = DataMapper.mapResponseToEntities(result.data)
                        populateAdapter(foods)
                        populateChart(foods)
                        populateNutritionFacts(foods)
                        populateFullNutrients(foods)
                        shimmer.hide(foods.isNullOrEmpty())
                    }
                    is ApiResponse.Error -> {
                        AppUtils.showToast(context, result.errorMessage)
                    }
                    is ApiResponse.Empty -> shimmer.hide(true)
                }
            }
        }
        viewModel.getNutrients().observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Success -> nutrientsData = result.data
                    is Resource.Error -> AppUtils.showToast(context, result.message)
                    is Resource.Loading -> Log.d(javaClass.simpleName, "Loading data")
                }
            }
        }
    }

    private fun populateAdapter(foods: List<FoodEntity?>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = FoodAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(foods)
    }

    private fun populateChart(foods: List<FoodEntity?>?) {
        var totalCalories = 0.0f
        var totalCarbohydrates = 0.0f
        var totalFats = 0.0f
        var totalProteins = 0.0f

        if (foods != null) {
            for (food in foods) {
                if (food != null) {
                    totalCalories += food.nfCalories!!.toFloat()
                    totalCarbohydrates += food.nfTotalCarbohydrate!!.toFloat()
                    totalFats += food.nfTotalFat!!.toFloat()
                    totalProteins += food.nfProtein!!.toFloat()
                }
            }
        }

        val caloriesFromCarbohydrates = totalCarbohydrates * 4
        val caloriesFromFats = totalFats * 9
        val caloriesFromProteins = totalProteins * 4

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

    private fun populateNutritionFacts(foods: List<FoodEntity?>) {
        for (food in foods) {
            if (food != null) {
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
                binding.layoutNutritionFacts.nfView.addData(nfData)
            }
        }
        binding.layoutNutritionFacts.nfView.drawLabel()
    }

    private fun getNutrientValueById(nutrients: ArrayList<Nutrient?>?, id: Int): Double {
        if (nutrients != null) for (nutrient in nutrients) if (nutrient?.id == id) return nutrient.value
            ?: 0.0
        return 0.0
    }

    private fun populateFullNutrients(foods: List<FoodEntity>) {
        binding.layoutNutritionFacts.tvFullNutrients.setOnClickListener {
            if (nutrientsData != null) {
                FullNutrientsFragment.newInstance(foods, nutrientsData!!)
                    .show(childFragmentManager, FullNutrientsFragment.TAG)
            }
        }
    }
}