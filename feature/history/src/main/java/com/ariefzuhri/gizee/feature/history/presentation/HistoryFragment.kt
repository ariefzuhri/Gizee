package com.ariefzuhri.gizee.feature.history.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.feature.history.databinding.FragmentHistoryBinding
import com.ariefzuhri.gizee.library.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.R as RApp
import com.ariefzuhri.gizee.adapter.HistoryAdapter
import com.ariefzuhri.gizee.core.common.R as RCore
import com.ariefzuhri.gizee.core.common.util.gone
import com.ariefzuhri.gizee.core.common.util.isNotEmpty
import com.ariefzuhri.gizee.core.database.domain.model.History
import com.ariefzuhri.gizee.feature.history.R
import com.ariefzuhri.gizee.feature.history.di.HistoryModule
import com.ariefzuhri.gizee.feature.home.presentation.HomeFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : MyBottomSheetDialogFragment(),
    HistoryAdapter.HistoryAdapterListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val module by lazy { HistoryModule }
    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        module.load()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        observeHistory()
    }

    private fun initToolbar() {
        binding.apply {
            toolbar.setNavigationOnClickListener { dismiss() }
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_clear) {
                    confirmHistoryRemoval()
                }
                true
            }
        }
    }

    private fun confirmHistoryRemoval() {
        AlertDialog.Builder(context)
            .setTitle(RCore.string.dialog_clear_history_title)
            .setMessage(RCore.string.dialog_clear_history_message)
            .setPositiveButton(RCore.string.yes) { _, _ -> viewModel.clearHistory() }
            .setNeutralButton(RCore.string.no, null)
            .create().show()
    }

    private fun observeHistory() {
        viewModel.history.observe(viewLifecycleOwner) { history ->
            populateAdapter(history)
        }
    }

    private fun populateAdapter(history: List<History?>) {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter =
                HistoryAdapter(this@HistoryFragment)
            adapter.submitList(history)
            recyclerView.adapter = adapter
            layoutEmpty.root.gone(adapter.isNotEmpty())
        }
    }

    override fun onHistoryClicked(history: History) {
        navigateToHome(history.query)
        dismiss()
    }

    private fun navigateToHome(query: String) {
        findNavController().setGraph(
            RApp.navigation.nav_main,
            HomeFragmentArgs(query).toBundle()
        )
    }

    override fun onDeleteHistory(history: History) {
        viewModel.deleteHistory(history)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        module.unload()
    }
}