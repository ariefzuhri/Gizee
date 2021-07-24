package com.ariefzuhri.gizee.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.data.source.local.entity.NutrientEntity
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.databinding.ItemNutrientBinding

class NutrientAdapter : RecyclerView.Adapter<NutrientAdapter.ViewHolder>() {

    private val nutrients = arrayListOf<NutrientEntity?>()

    fun submitList(nutrients: List<NutrientEntity?>) {
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
        nutrient?.let { holder.bind(it) }
    }

    override fun getItemCount() = nutrients.size

    inner class ViewHolder(private val binding: ItemNutrientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nutrient: NutrientEntity) {
            binding.tvName.text = nutrient.name
            binding.tvWeight.text = itemView.context.resources.getString(
                R.string.weight,
                AppUtils.getDecimalFormat(nutrient.value),
                nutrient.unit
            )
        }
    }
}