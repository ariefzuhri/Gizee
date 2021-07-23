package com.ariefzuhri.gizee.fullnutrients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.Nutrient
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.ui.adapter.NutrientAdapter
import com.ariefzuhri.gizee.core.ui.customview.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.databinding.FragmentFullNutrientsBinding

private const val ARG_FOODS = "foods"
private const val ARG_NUTRIENTS = "nutrients"

class FullNutrientsFragment : MyBottomSheetDialogFragment() {

    private lateinit var binding: FragmentFullNutrientsBinding
    private var foods: List<FoodEntity>? = null
    private var nutrientsData: List<NutrientEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            foods = it.getParcelableArrayList(ARG_FOODS)
            nutrientsData = it.getParcelableArrayList(ARG_NUTRIENTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFullNutrientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG: String = FullNutrientsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(foods: List<FoodEntity>, nutrients: List<NutrientEntity>) =
            FullNutrientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FOODS, foods as ArrayList<FoodEntity>)
                    putParcelableArrayList(ARG_NUTRIENTS, nutrients as ArrayList<NutrientEntity>)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { dismiss() }

        val nutrients = mergedNutrients(nutrientsData, foods)
        populateAdapter(nutrients)
    }

    private fun mergedNutrients(
        nutrientsData: List<NutrientEntity>?,
        foods: List<FoodEntity>?
    ): ArrayList<Nutrient?> {
        var nutrients = arrayListOf<Nutrient?>()
        for (nutrient in nutrientsData!!) nutrients.add(Nutrient(nutrient.id, 0.0))
        for (food in foods!!) {
            for (nutrient in food.fullNutrients!!) {
                nutrients = addValueById(nutrient!!, nutrients)
            }
        }
        return nutrients
    }

    private fun addValueById(
        unmergedNutrient: Nutrient,
        nutrients: ArrayList<Nutrient?>
    ): ArrayList<Nutrient?> {
        for ((i, mergedNutrient) in nutrients.withIndex()) {
            if (mergedNutrient?.id == unmergedNutrient.id) {
                nutrients[i]?.value = nutrients[i]?.value?.plus(unmergedNutrient.value!!)
            }
        }
        return nutrients
    }

    private fun populateAdapter(nutrients: ArrayList<Nutrient?>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NutrientAdapter(nutrientsData!!)
        adapter.submitList(nutrients)
        binding.recyclerView.adapter = adapter
    }
}