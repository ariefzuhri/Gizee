package com.ariefzuhri.gizee.feature.nutritionfacts.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ariefzuhri.gizee.NavMainDirections
import com.ariefzuhri.gizee.feature.nutritionfacts.databinding.FragmentNutritionFactsBinding
import com.ariefzuhri.gizee.core.common.wrapper.Resource
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient
import com.ariefzuhri.gizee.core.common.util.gone
import com.ariefzuhri.gizee.core.common.util.showToast
import com.ariefzuhri.gizee.core.common.util.toDecimal
import com.ariefzuhri.gizee.feature.nutritionfacts.di.NutritionFactsModule
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_FOODS = "foods"

class NutritionFactsFragment : Fragment() {

    private var _binding: FragmentNutritionFactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var foods: List<Food>
    private val module by lazy { NutritionFactsModule }
    private val viewModel: NutritionFactsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        module.load()

        foods = arguments?.getParcelableArrayList(ARG_FOODS) ?: listOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNutritionFactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(foods: ArrayList<Food?>?) =
            NutritionFactsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FOODS, foods)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNutrients()
        populateChart()
        populateNutritionFacts()
    }

    private fun observeNutrients() {
        viewModel.foods = foods
        viewModel.nutrients.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> binding.tvFullNutrients.gone(true)
                    is Resource.Success -> {
                        val rawNutrients = result.data
                        if (!(foods.isEmpty() && rawNutrients.isNullOrEmpty())) {
                            populateFullNutrients(foods, rawNutrients!!)
                        }
                        binding.tvFullNutrients.gone(false)
                    }
                    is Resource.Error -> activity.showToast(result.message)
                }
            }
        }
    }

    private fun populateChart() {
        val chartModel = AAChartModel()
            .chartType(AAChartType.Pie)
            .title("Source of Calories")
            .subtitle("Total calories: ${viewModel.totalCalories.toDecimal()} kcal")
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#61AAEE", "#EBEE61", "#64EE61"))
            .series(
                arrayOf(
                    AASeriesElement()
                        .dataLabels(
                            AADataLabels().format(
                                "<b>{point.name}</b>: {point.percentage:.1f}%"
                            )
                        )
                        .name("Total calories (kcal)")
                        .data(
                            arrayOf(
                                arrayOf("Carbohydrates", viewModel.caloriesFromCarbohydrates),
                                arrayOf("Fats", viewModel.caloriesFromFats),
                                arrayOf("Proteins", viewModel.caloriesFromProteins),
                            )
                        )
                )
            )
        binding.chartCalories.aa_drawChartWithChartModel(chartModel)
    }

    private fun populateNutritionFacts() {
        binding.nfView.data = viewModel.nfData
        binding.nfView.drawLabel()
    }

    private fun populateFullNutrients(foods: List<Food>, rawNutrients: List<Nutrient>) {
        binding.tvFullNutrients.setOnClickListener {
            navigateToFullNutrients(foods.toTypedArray(), rawNutrients.toTypedArray())
        }
    }

    private fun navigateToFullNutrients(
        foods: Array<Food>,
        rawNutrients: Array<Nutrient>,
    ) {
        findNavController().navigate(
            NavMainDirections.actionGlobalToFullNutrients(foods, rawNutrients)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        module.unload()
    }
}