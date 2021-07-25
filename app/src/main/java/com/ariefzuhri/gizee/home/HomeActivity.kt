package com.ariefzuhri.gizee.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.databinding.ActivityHomeBinding
import com.ariefzuhri.gizee.home.nutrientsearch.NutrientSearchFragment
import com.ariefzuhri.gizee.home.searchresult.SearchResultFragment

class HomeActivity : AppCompatActivity(), HomeCallback {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        populateMainFragment()
    }

    private fun populateMainFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(NutrientSearchFragment.TAG)
        if (fragment == null) {
            fragment = NutrientSearchFragment.newInstance()
            fragmentTransaction.add(binding.container.id, fragment, NutrientSearchFragment.TAG)
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount <= 1) finish() else super.onBackPressed()
    }

    override fun openSearchResult(query: String) {
        val fragment = SearchResultFragment.newInstance(query)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.enter_from_bottom,
                R.anim.exit_to_top,
                R.anim.enter_from_top,
                R.anim.exit_to_bottom
            )
            .add(binding.container.id, fragment, SearchResultFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun getQueryFromHistory(history: History) {
        findViewById<EditText>(R.id.edt_search).setText(history.query)
    }
}