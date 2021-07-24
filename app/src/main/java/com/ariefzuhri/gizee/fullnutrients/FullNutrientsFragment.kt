package com.ariefzuhri.gizee.fullnutrients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.ui.adapter.NutrientAdapter
import com.ariefzuhri.gizee.core.ui.customview.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.databinding.FragmentFullNutrientsBinding

private const val ARG_FOODS = "foods"
private const val ARG_NUTRIENTS = "nutrients"

class FullNutrientsFragment : MyBottomSheetDialogFragment() {

    private lateinit var binding: FragmentFullNutrientsBinding
    private var foods: List<FoodEntity>? = null
    private var rawNutrients: List<NutrientEntity>? = null

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
        binding = FragmentFullNutrientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG: String = FullNutrientsFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(foods: List<FoodEntity>, rawNutrients: List<NutrientEntity>) =
            FullNutrientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FOODS, foods as ArrayList<FoodEntity>)
                    putParcelableArrayList(ARG_NUTRIENTS, rawNutrients as ArrayList<NutrientEntity>)
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
        foods: List<FoodEntity>?,
        rawNutrients: List<NutrientEntity>?
    ): List<NutrientEntity> {
        var result = arrayListOf<NutrientEntity>()
        for (rawNutrient in rawNutrients!!) result.add(
            NutrientEntity(
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
        unmergedNutrient: NutrientEntity,
        nutrients: ArrayList<NutrientEntity>
    ): ArrayList<NutrientEntity> {
        for ((i, mergedNutrient) in nutrients.withIndex()) {
            if (mergedNutrient.id == unmergedNutrient.id) {
                nutrients[i].value = nutrients[i].value?.plus(unmergedNutrient.value!!)
            }
        }
        return nutrients
    }

    private fun populateAdapter(nutrients: List<NutrientEntity?>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NutrientAdapter()
        adapter.submitList(nutrients)
        binding.recyclerView.adapter = adapter
    }
}