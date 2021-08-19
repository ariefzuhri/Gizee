package com.ariefzuhri.gizee.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.view.get
import androidx.core.view.isNotEmpty
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.utils.EXTRA_FOOD
import com.ariefzuhri.gizee.databinding.ActivityDetailsBinding
import com.ariefzuhri.gizee.core.utils.TAG
import com.ariefzuhri.gizee.core.utils.bindLoadImage
import com.ariefzuhri.gizee.core.utils.formatToDecimal
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsFragment
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
            if (isFavorite) menuFavorite.setIcon(R.drawable.ic_menu_favorite)
            else menuFavorite.setIcon(R.drawable.ic_menu_unfavorite)
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
            bindLoadImage(imgPhoto, food.photo)
            tvTitle.text = food.name.capitalize(Locale.current)
            tvCalories.text = getString(
                R.string.calories,
                formatToDecimal(food.nfCalories)
            )
            tvMeasure.text = getString(
                R.string.measure,
                formatToDecimal(food.servingQty),
                food.servingUnit
            )
            tvWeight.text = getString(
                R.string.weight,
                formatToDecimal(food.servingWeightGrams),
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