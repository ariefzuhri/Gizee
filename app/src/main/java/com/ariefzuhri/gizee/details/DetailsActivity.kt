package com.ariefzuhri.gizee.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.core.view.isNotEmpty
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.databinding.ActivityDetailsBinding
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.TAG
import com.ariefzuhri.gizee.core.utils.Constants.EXTRA_FOOD
import com.ariefzuhri.gizee.core.utils.ViewBinding
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsFragment
import org.apache.commons.lang3.StringUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(EXTRA_FOOD)) {
            val food: Food = intent.getParcelableExtra(EXTRA_FOOD)!!
            initToolbar()
            initContent(food)
            populateNutritionFacts(arrayListOf(food))
            viewModel.food = food
        } else onBackPressed()

        viewModel.isFavorite().observe(this) { isFavorite ->
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
            if (isFavorite) menuFavorite.setIcon(R.drawable.ic_bookmark)
            else menuFavorite.setIcon(R.drawable.ic_bookmark_outline)
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_favorite) {
                viewModel.setNewStateFavorite()
            }
            true
        }
    }

    private fun initContent(food: Food) {
        with(binding) {
            ViewBinding.bindLoadImage(imgPhoto, food.photo)
            tvTitle.text = StringUtils.capitalize(food.name)
            tvCalories.text = getString(
                R.string.calories,
                AppUtils.formatToDecimal(food.nfCalories)
            )
            tvMeasure.text = getString(
                R.string.measure,
                AppUtils.formatToDecimal(food.servingQty),
                food.servingUnit
            )
            tvWeight.text = getString(
                R.string.weight,
                AppUtils.formatToDecimal(food.servingWeightGrams),
                "g"
            )
        }
    }

    private fun populateNutritionFacts(foods: List<Food?>?) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(NutritionFactsFragment.TAG)
        if (fragment == null) {
            fragment = NutritionFactsFragment.newInstance(foods)
            fragmentTransaction.add(binding.container.id, fragment, NutritionFactsFragment.TAG)
        }
        fragmentTransaction.commit()
    }
}