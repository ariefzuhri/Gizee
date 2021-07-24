package com.ariefzuhri.gizee.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.domain.model.Nutrient
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.databinding.ItemNutrientBinding

class NutrientAdapter : RecyclerView.Adapter<NutrientAdapter.ViewHolder>() {

    private val nutrients = arrayListOf<Nutrient?>()

    fun submitList(nutrients: List<Nutrient?>) {
        with(this.nutrients) {
            clear()
            addAll(nutrients)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemNutrientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nutrient = nutrients[position]
        holder.bind(nutrient)
    }

    override fun getItemCount() = nutrients.size

    inner class ViewHolder(private val binding: ItemNutrientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nutrient: Nutrient?) {
            binding.tvName.text = nutrient?.name
            binding.tvWeight.text = itemView.context.resources.getString(
                R.string.weight,
                AppUtils.getDecimalFormat(nutrient?.value),
                nutrient?.unit
            )
        }
    }
}