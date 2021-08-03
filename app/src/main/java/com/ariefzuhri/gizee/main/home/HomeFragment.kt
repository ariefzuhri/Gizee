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
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.KeyboardUtils
import com.ariefzuhri.gizee.databinding.FragmentHomeBinding
import com.ariefzuhri.gizee.main.history.HistoryFragment
import com.ariefzuhri.gizee.main.MainActivity
import com.ariefzuhri.gizee.main.MainCallback

class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainCallback: MainCallback

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
        val TAG: String = HomeFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibFavorites.setOnClickListener(this)
        binding.cvHelp.setOnClickListener(this)
        binding.tvHistory.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.ibFavorites.id -> openFavorites()
            binding.cvHelp.id -> autofillQuery()
            binding.tvHistory.id -> showHistory()
            binding.btnSearch.id -> performSearch()
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
            KeyboardUtils.hideSoftKeyboard(binding.edtSearch)
            mainCallback.openSearchResult(query)
        } else {
            KeyboardUtils.showSoftKeyboard(binding.edtSearch)
            binding.edtSearch.requestFocus()
            AppUtils.showToast(
                context,
                resources.getString(R.string.toast_empty_search_field)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}