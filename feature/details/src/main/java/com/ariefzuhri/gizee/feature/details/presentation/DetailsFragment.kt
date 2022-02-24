package com.ariefzuhri.gizee.feature.details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.view.get
import androidx.core.view.isNotEmpty
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ariefzuhri.gizee.core.common.util.TAG
import com.ariefzuhri.gizee.feature.details.databinding.FragmentDetailsBinding
import com.ariefzuhri.gizee.core.common.R as RCore
import com.ariefzuhri.gizee.core.common.util.loadRounded
import com.ariefzuhri.gizee.core.common.util.toDecimal
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.feature.details.R
import com.ariefzuhri.gizee.feature.details.di.DetailsModule
import com.ariefzuhri.gizee.feature.nutritionfacts.presentation.NutritionFactsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val module by lazy { DetailsModule }
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        module.load()

        initView()
        observeIsFavorite()
    }

    private fun initView() {
        args.food?.let {
            val food = it
            initToolbar()
            initContent(food)
            populateNutritionFacts(arrayListOf(food))
            viewModel.food = food
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_favorite) {
                viewModel.setNewStateFavorite()
            }
            true
        }
    }

    private fun initContent(food: Food) {
        binding.apply {
            imgPhoto.loadRounded(food.photo)
            tvTitle.text = food.name.capitalize(Locale.current)
            tvCalories.text = getString(
                RCore.string.calories,
                food.nfCalories.toDecimal()
            )
            tvMeasure.text = getString(
                RCore.string.measure,
                food.servingQuantity.toDecimal(),
                food.servingUnit
            )
            tvWeight.text = getString(
                RCore.string.weight,
                food.servingWeightGrams.toDecimal(),
                "g"
            )
        }
    }

    private fun populateNutritionFacts(foods: ArrayList<Food?>?) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        var fragment = childFragmentManager.findFragmentByTag(NutritionFactsFragment.TAG)
        if (fragment == null) {
            fragment = NutritionFactsFragment.newInstance(foods)
            fragmentTransaction.add(
                binding.fcvNutritionFacts.id,
                fragment, NutritionFactsFragment.TAG
            )
        }
        fragmentTransaction.commit()
    }

    private fun observeIsFavorite() {
        viewModel.isFavorite().observe(viewLifecycleOwner) { isFavorite ->
            setFavoriteState(isFavorite)
        }
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        val food = viewModel.food!!
        food.isFavorite = isFavorite
        viewModel.food = food

        val toolbarMenu = binding.toolbar.menu
        if (toolbarMenu.isNotEmpty()) {
            val menuFavorite = toolbarMenu[0]
            menuFavorite.setIcon(
                if (isFavorite) RCore.drawable.ic_menu_favorite
                else RCore.drawable.ic_menu_unfavorite
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        module.unload()
    }
}