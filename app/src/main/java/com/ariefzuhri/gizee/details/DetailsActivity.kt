package com.ariefzuhri.gizee.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
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
            viewModel.setFood(food)
        } else onBackPressed()

        viewModel.food.observe(this) { food ->
            initializeToolbar()
            initializeContent(food)
            populateNutritionFacts(arrayListOf(food))
            setFavoriteState(food.isFavorite)
        }
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        val menuFavorite = binding.toolbar.menu[0]
        if (isFavorite) menuFavorite.setIcon(R.drawable.ic_bookmark)
        else menuFavorite.setIcon(R.drawable.ic_bookmark_outline)
    }

    private fun initializeToolbar() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_favorite) {
                viewModel.setNewStateFavorite()
            }
            true
        }
    }

    private fun initializeContent(food: Food) {
        ViewBinding.bindLoadImage(binding.imgPhoto, food.photo)
        binding.tvTitle.text = StringUtils.capitalize(food.name)
        binding.tvCalories.text = getString(
            R.string.calories,
            AppUtils.getDecimalFormat(food.nfCalories)
        )
        binding.tvMeasure.text = getString(
            R.string.measure,
            AppUtils.getDecimalFormat(food.servingQty),
            food.servingUnit
        )
        binding.tvWeight.text = getString(
            R.string.weight,
            AppUtils.getDecimalFormat(food.servingWeightGrams),
            "g"
        )
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