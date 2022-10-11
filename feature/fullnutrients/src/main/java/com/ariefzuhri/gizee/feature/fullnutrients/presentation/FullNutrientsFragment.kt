package com.ariefzuhri.gizee.feature.fullnutrients.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.feature.fullnutrients.databinding.FragmentFullNutrientsBinding
import com.ariefzuhri.gizee.library.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient
import com.ariefzuhri.gizee.adapter.NutrientAdapter
import com.ariefzuhri.gizee.feature.fullnutrients.di.FullNutrientsModule
import org.koin.androidx.viewmodel.ext.android.viewModel

class FullNutrientsFragment : MyBottomSheetDialogFragment() {

    private val args: FullNutrientsFragmentArgs by navArgs()

    private var _binding: FragmentFullNutrientsBinding? = null
    private val binding get() = _binding!!

    private lateinit var foods: List<Food>
    private val module by lazy { FullNutrientsModule }
    private lateinit var rawNutrients: List<Nutrient>
    private val viewModel: FullNutrientsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        module.load()

        foods = args.foods.orEmpty().toList()
        rawNutrients = args.rawNutrients.orEmpty().toList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFullNutrientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        observeNutrients()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    private fun observeNutrients() {
        viewModel.setNutrients(rawNutrients, foods)
        viewModel.nutrients?.let { populateAdapter(it) }
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
        module.unload()
    }
}