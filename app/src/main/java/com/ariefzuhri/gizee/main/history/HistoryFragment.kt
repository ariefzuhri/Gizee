package com.ariefzuhri.gizee.main.history

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.core.ui.adapter.HistoryAdapter
import com.ariefzuhri.gizee.core.ui.adapter.HistoryAdapterListener
import com.ariefzuhri.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.core.utils.gone
import com.ariefzuhri.gizee.core.utils.isNotEmpty
import com.ariefzuhri.gizee.databinding.FragmentHistoryBinding
import com.ariefzuhri.gizee.main.MainActivity
import com.ariefzuhri.gizee.main.MainCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : MyBottomSheetDialogFragment(), HistoryAdapterListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var mainCallback: MainCallback? = null
    private val viewModel: HistoryViewModel by viewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainCallback = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        viewModel.history.observe(viewLifecycleOwner) { history ->
            populateAdapter(history)
        }
    }

    private fun initToolbar() {
        with(binding) {
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

    private fun populateAdapter(history: List<History?>) {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = HistoryAdapter(this@HistoryFragment)
            adapter.submitList(history)
            recyclerView.adapter = adapter
            layoutEmpty.root.gone(adapter.isNotEmpty())
        }
    }

    override fun onHistoryClicked(history: History) {
        mainCallback?.setSelectedQuery(history.query)
        dismiss()
    }

    override fun onDeleteHistory(history: History) {
        viewModel.deleteHistory(history)
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