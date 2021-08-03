package com.ariefzuhri.gizee.fullnutrients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.ui.adapter.NutrientAdapter
import com.ariefzuhri.gizee.core.ui.customview.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.databinding.FragmentFullNutrientsBinding

private const val ARG_FOODS = "foods"
private const val ARG_NUTRIENTS = "nutrients"

class FullNutrientsFragment : MyBottomSheetDialogFragment() {

    private var _binding: FragmentFullNutrientsBinding? = null
    private val binding get() = _binding!!

    private var foods: List<Food>? = null
    private var rawNutrients: List<Nutrient>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            foods = it.getParcelableArrayList(ARG_FOODS)
            rawNutrients = it.getParcelableArrayList(ARG_NUTRIENTS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullNutrientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG: String = FullNutrientsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(foods: List<Food>, rawNutrients: List<Nutrient>) =
            FullNutrientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FOODS, foods as ArrayList<Food>)
                    putParcelableArrayList(ARG_NUTRIENTS, rawNutrients as ArrayList<Nutrient>)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { dismiss() }

        val nutrients = mergedValueNutrients(foods, rawNutrients)
        populateAdapter(nutrients)
    }

    private fun mergedValueNutrients(
        foods: List<Food>?,
        rawNutrients: List<Nutrient>?
    ): List<Nutrient> {
        var result = arrayListOf<Nutrient>()
        for (rawNutrient in rawNutrients!!) result.add(
            Nutrient(
                rawNutrient.id,
                rawNutrient.name,
                rawNutrient.unit,
                0.0
            )
        )

        // Gabungkan value semua nutrient pada setiap food
        for (food in foods!!) {
            // Tambah value nutrient di foods ke rawNutrients sesuai id-nya
            for (onlyValueNutrient in food.fullNutrients!!) {
                result = addValueById(onlyValueNutrient!!, result)
            }
        }
        return result
    }

    private fun addValueById(
        unmergedNutrient: Nutrient,
        nutrients: ArrayList<Nutrient>
    ): ArrayList<Nutrient> {
        for ((i, mergedNutrient) in nutrients.withIndex()) {
            if (mergedNutrient.id == unmergedNutrient.id) {
                nutrients[i].value = nutrients[i].value?.plus(unmergedNutrient.value!!)
            }
        }
        return nutrients
    }

    private fun populateAdapter(nutrients: List<Nutrient?>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NutrientAdapter()
        adapter.submitList(nutrients)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}