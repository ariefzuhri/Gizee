package com.ariefzuhri.gizee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.core.common.R
import com.ariefzuhri.gizee.core.common.databinding.ItemNutrientBinding
import com.ariefzuhri.gizee.core.common.util.toDecimal
import com.ariefzuhri.gizee.core.database.domain.model.Nutrient

class NutrientAdapter : RecyclerView.Adapter<NutrientAdapter.ViewHolder>() {

    private val nutrients = arrayListOf<Nutrient?>()

    fun submitList(nutrients: List<Nutrient?>?) {
        nutrients?.let {
            with(this.nutrients) {
                clear()
                addAll(it)
                notifyItemRangeInserted(0, it.size)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding = ItemNutrientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nutrient = nutrients[position]
        nutrient?.let { holder.bind(it) }
    }

    override fun getItemCount() = nutrients.size

    inner class ViewHolder(private val binding: ItemNutrientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nutrient: Nutrient) {
            binding.tvName.text = nutrient.name
            binding.tvWeight.text = itemView.context.resources.getString(
                R.string.weight,
                nutrient.value.toDecimal(),
                nutrient.unit
            )
        }
    }
}