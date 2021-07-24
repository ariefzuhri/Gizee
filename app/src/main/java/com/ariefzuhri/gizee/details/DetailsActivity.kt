package com.ariefzuhri.gizee.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.databinding.ActivityDetailsBinding
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.Constants.Companion.EXTRA_FOOD
import com.ariefzuhri.gizee.core.ui.viewmodel.ViewModelFactory
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsFragment
import org.apache.commons.lang3.StringUtils

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var food: Food
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(EXTRA_FOOD)) {
            food = intent.getParcelableExtra(EXTRA_FOOD)!!
        } else onBackPressed()

        initializeToolbar()

        AppUtils.loadImage(this, binding.imgPhoto, food.photo)
        binding.tvTitle.text = StringUtils.capitalize(food.name)
        binding.tvCalories.text =
            resources.getString(R.string.calories, AppUtils.getDecimalFormat(food.nfCalories))
        binding.tvMeasure.text = resources.getString(
            R.string.measure,
            AppUtils.getDecimalFormat(food.servingQty),
            food.servingUnit
        )
        binding.tvWeight.text = resources.getString(
            R.string.weight,
            AppUtils.getDecimalFormat(food.servingWeightGrams),
            "g"
        )

        populateNutritionFacts(arrayListOf(food))

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailsViewModel::class.java]
        viewModel.isFavorite(food.id!!).observe(this) { result ->
            food.isFavorite = result.id != null
            setFavoriteIcon(food.isFavorite!!)
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        val menuFavorite = binding.toolbar.menu[0]
        if (isFavorite) menuFavorite.setIcon(R.drawable.ic_bookmark)
        else menuFavorite.setIcon(R.drawable.ic_bookmark_outline)
    }

    private fun initializeToolbar() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_favorite) {
                val newState = !food.isFavorite!!
                viewModel.setFavorite(food, newState)
            }
            true
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