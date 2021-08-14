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
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_FOODS = "foods"
private const val ARG_RAW_NUTRIENTS = "raw_nutrients"

class FullNutrientsFragment : MyBottomSheetDialogFragment() {

    private var _binding: FragmentFullNutrientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var foods: List<Food>
    private lateinit var rawNutrients: List<Nutrient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            foods = it.getParcelableArrayList(ARG_FOODS)!!
            rawNutrients = it.getParcelableArrayList(ARG_RAW_NUTRIENTS)!!
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
        @JvmStatic
        fun newInstance(foods: List<Food>, rawNutrients: List<Nutrient>) =
            FullNutrientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FOODS, foods as ArrayList<Food>)
                    putParcelableArrayList(ARG_RAW_NUTRIENTS, rawNutrients as ArrayList<Nutrient>)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        val viewModel: FullNutrientsViewModel by viewModel()
        viewModel.setNutrients(rawNutrients, foods)
        viewModel.nutrients?.let { populateAdapter(it) }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    private fun populateAdapter(fullNutrients: List<Nutrient>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NutrientAdapter()
        adapter.submitList(fullNutrients)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}