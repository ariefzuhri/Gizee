package com.ariefzuhri.gizee.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.core.domain.model.History
import com.ariefzuhri.gizee.databinding.ItemHistoryBinding

class HistoryAdapter(private val listener: HistoryAdapterListener) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val history = arrayListOf<History?>()

    fun submitList(history: List<History?>) {
        with(this.history) {
            clear()
            addAll(history)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = history[position]
        holder.bind(history)
    }

    override fun getItemCount() = history.size

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History?) {
            binding.tvName.text = history?.query
            history?.let {
                binding.ibDelete.setOnClickListener { listener.onDeleteHistory(history) }
                itemView.setOnClickListener { listener.onHistoryClicked(history) }
            }
        }
    }
}

interface HistoryAdapterListener {

    fun onHistoryClicked(history: History)

    fun onDeleteHistory(history: History)
}