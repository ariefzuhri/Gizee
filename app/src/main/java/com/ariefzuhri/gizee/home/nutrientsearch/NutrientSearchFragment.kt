package com.ariefzuhri.gizee.home.nutrientsearch

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.KeyboardUtils
import com.ariefzuhri.gizee.databinding.FragmentNutrientSearchBinding
import com.ariefzuhri.gizee.home.history.HistoryFragment
import com.ariefzuhri.gizee.home.HomeActivity
import com.ariefzuhri.gizee.home.HomeCallback

class NutrientSearchFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentNutrientSearchBinding
    private lateinit var homeCallback: HomeCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeCallback = context as HomeActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutrientSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG: String = NutrientSearchFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = NutrientSearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibFavorites.setOnClickListener(this)
        binding.tvHistory.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.ibFavorites.id -> openFavorites()
            binding.tvHistory.id -> showHistory()
            binding.btnSearch.id -> performSearch()
        }
    }

    private fun openFavorites() {
        val uri = Uri.parse("gizee://favorites")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun showHistory() {
        HistoryFragment.newInstance().show(childFragmentManager, HistoryFragment.TAG)
    }

    private fun performSearch() {
        val query = binding.edtSearch.text.toString()
        if (query.isNotEmpty()) {
            KeyboardUtils.hideSoftKeyboard(binding.edtSearch)
            homeCallback.openSearchResult(query)
        } else {
            KeyboardUtils.showSoftKeyboard(binding.edtSearch)
            binding.edtSearch.requestFocus()
            AppUtils.showToast(
                context,
                resources.getString(R.string.toast_empty_search_field)
            )
        }
    }
}