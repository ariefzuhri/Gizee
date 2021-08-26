package com.ariefzuhri.gizee.main.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.utils.TAG
import com.ariefzuhri.gizee.core.utils.hideKeyboard
import com.ariefzuhri.gizee.core.utils.showKeyboard
import com.ariefzuhri.gizee.core.utils.showToast
import com.ariefzuhri.gizee.databinding.FragmentHomeBinding
import com.ariefzuhri.gizee.main.history.HistoryFragment
import com.ariefzuhri.gizee.main.MainActivity
import com.ariefzuhri.gizee.main.MainCallback

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var mainCallback: MainCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainCallback = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ibFavorites.setOnClickListener(this@HomeFragment)
            cvHelp.setOnClickListener(this@HomeFragment)
            tvHistory.setOnClickListener(this@HomeFragment)
            btnSearch.setOnClickListener(this@HomeFragment)
        }
    }

    override fun onClick(view: View) {
        with(binding) {
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
        HistoryFragment.newInstance().show(childFragmentManager, HistoryFragment.TAG)
    }

    private fun performSearch() {
        val query = binding.edtSearch.text.toString()
        if (query.isNotEmpty()) {
            binding.edtSearch.hideKeyboard()
            mainCallback?.openSearchResult(query)
        } else {
            binding.edtSearch.showKeyboard()
            binding.edtSearch.requestFocus()
            activity?.showToast(R.string.toast_empty_search_field)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()
        mainCallback = null
    }
}