package com.ariefzuhri.gizee.main.history

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.ui.adapter.HistoryAdapter
import com.ariefzuhri.gizee.core.ui.adapter.HistoryAdapterListener
import com.ariefzuhri.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.core.utils.gone
import com.ariefzuhri.gizee.core.utils.isNotEmpty
import com.ariefzuhri.gizee.databinding.FragmentHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : MyBottomSheetDialogFragment(), HistoryAdapterListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
            .setTitle(R.string.dialog_clear_history_title)
            .setMessage(R.string.dialog_clear_history_message)
            .setPositiveButton(R.string.yes) { _, _ -> viewModel.clearHistory() }
            .setNeutralButton(R.string.no, null)
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
            val adapter = HistoryAdapter(this@HistoryFragment)
            adapter.submitList(history)
            recyclerView.adapter = adapter
            layoutEmpty.root.gone(adapter.isNotEmpty())
        }
    }

    override fun onHistoryClicked(history: History) {
        findNavController().navigate(
            HistoryFragmentDirections.actionReturnToHome(history.query)
        )
    }

    override fun onDeleteHistory(history: History) {
        viewModel.deleteHistory(history)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}