package com.ariefzuhri.gizee.feature.home.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ariefzuhri.gizee.core.common.R
import com.ariefzuhri.gizee.core.common.util.cleanup
import com.ariefzuhri.gizee.feature.home.databinding.FragmentHomeBinding
import com.ariefzuhri.gizee.core.common.util.hideKeyboard
import com.ariefzuhri.gizee.core.common.util.showKeyboard
import com.ariefzuhri.gizee.core.common.util.showToast

class HomeFragment : Fragment(), View.OnClickListener {

    private val args: HomeFragmentArgs by navArgs()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.apply {
            edtSearch.setText(args.selectedQuery)
            ibFavorites.setOnClickListener(this@HomeFragment)
            cvHelp.setOnClickListener(this@HomeFragment)
            tvHistory.setOnClickListener(this@HomeFragment)
            btnSearch.setOnClickListener(this@HomeFragment)
        }
    }

    override fun onClick(view: View) {
        binding.apply {
            when (view.id) {
                ibFavorites.id -> navigateToFavorites()
                cvHelp.id -> autofillQuery()
                tvHistory.id -> navigateToHistory()
                btnSearch.id -> performSearch()
            }
        }
    }

    private fun navigateToFavorites() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToFavorites()
        )
    }

    private fun autofillQuery() {
        binding.edtSearch.setText(R.string.autofill_query)
    }

    private fun navigateToHistory() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToHistory()
        )
    }

    private fun performSearch() {
        val query = binding.edtSearch.text.toString().cleanup()
        if (query.isNotEmpty()) {
            binding.edtSearch.hideKeyboard()
            navigateToSearchResults(query)
        } else {
            binding.edtSearch.showKeyboard()
            activity.showToast(R.string.toast_empty_search_field)
        }
    }

    private fun navigateToSearchResults(query: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToSearchResults(query)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}