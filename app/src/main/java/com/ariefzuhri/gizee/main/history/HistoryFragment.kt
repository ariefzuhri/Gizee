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
import com.ariefzuhri.gizee.core.ui.customview.bottomsheet.MyBottomSheetDialogFragment
import com.ariefzuhri.gizee.databinding.FragmentHistoryBinding
import com.ariefzuhri.gizee.main.MainActivity
import com.ariefzuhri.gizee.main.MainCallback
import com.ariefzuhri.gizee.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : MyBottomSheetDialogFragment(), HistoryAdapterListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainCallback: MainCallback
    private val viewModel: MainViewModel by viewModel()

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

        initializeToolbar()

        viewModel.getHistory.observe(viewLifecycleOwner) { history ->
            populateAdapter(history)
        }
    }

    private fun initializeToolbar() {
        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_clear) {
                AlertDialog.Builder(context)
                    .setTitle(R.string.dialog_clear_history_title)
                    .setMessage(R.string.dialog_clear_history_message)
                    .setPositiveButton(R.string.yes) { _, _ -> viewModel.clearHistory() }
                    .setNeutralButton(R.string.no, null)
                    .create().show()
            }
            true
        }
    }

    private fun populateAdapter(history: List<History?>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = HistoryAdapter(this)
        adapter.submitList(history)
        binding.recyclerView.adapter = adapter

        if (adapter.itemCount > 0) binding.viewEmpty.root.visibility = View.GONE
        else binding.viewEmpty.root.visibility = View.VISIBLE
    }

    override fun onHistoryClicked(history: History) {
        dismiss()
        mainCallback.getQueryFromHistory(history)
    }

    override fun onDeleteHistory(history: History) {
        viewModel.deleteHistory(history)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}