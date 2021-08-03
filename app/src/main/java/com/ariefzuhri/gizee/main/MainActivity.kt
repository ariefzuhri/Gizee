package com.ariefzuhri.gizee.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.utils.AppUtils.TAG
import com.ariefzuhri.gizee.databinding.ActivityMainBinding
import com.ariefzuhri.gizee.main.home.HomeFragment
import com.ariefzuhri.gizee.main.searchresult.SearchResultFragment

class MainActivity : AppCompatActivity(), MainCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        populateMainFragment()
    }

    private fun populateMainFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG)
        if (fragment == null) {
            fragment = HomeFragment.newInstance()
            fragmentTransaction.add(binding.container.id, fragment, HomeFragment.TAG)
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