package com.ariefzuhri.gizee.main.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.utils.hideKeyboard
import com.ariefzuhri.gizee.core.utils.showKeyboard
import com.ariefzuhri.gizee.core.utils.showToast
import com.ariefzuhri.gizee.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {

    private val args: HomeFragmentArgs by navArgs()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            edtSearch.setText(args.savedQuery)
            ibFavorites.setOnClickListener(this@HomeFragment)
            cvHelp.setOnClickListener(this@HomeFragment)
            tvHistory.setOnClickListener(this@HomeFragment)
            btnSearch.setOnClickListener(this@HomeFragment)
        }
    }

    override fun onClick(view: View) {
        binding.apply {
            when (view.id) {
                ibFavorites.id -> openFavorites()
                cvHelp.id -> autofillQuery()
                tvHistory.id -> showHistory()
                btnSearch.id -> performSearch()
            }
        }
    }

    private fun openFavorites() {
        val uri = Uri.parse("gizee://favorites")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun autofillQuery() {
        binding.edtSearch.setText(R.string.autofill_query)
    }

    private fun showHistory() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToHistory()
        )
    }

    private fun performSearch() {
        val query = binding.edtSearch.text.toString()
        if (query.isNotEmpty()) {
            binding.edtSearch.hideKeyboard()
            openSearchResult(query)
        } else {
            binding.edtSearch.showKeyboard()
            binding.edtSearch.requestFocus()
            activity.showToast(R.string.toast_empty_search_field)
        }
    }

    private fun openSearchResult(query: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeToSearchResult(query)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}