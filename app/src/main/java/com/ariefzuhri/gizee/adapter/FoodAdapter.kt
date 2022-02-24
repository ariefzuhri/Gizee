package com.ariefzuhri.gizee.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.NavMainDirections
import com.ariefzuhri.gizee.core.common.R
import com.ariefzuhri.gizee.core.common.databinding.ItemFoodBinding
import com.ariefzuhri.gizee.core.common.util.loadRounded
import com.ariefzuhri.gizee.core.common.util.toDecimal
import com.ariefzuhri.gizee.core.database.domain.model.Food

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private val foods = arrayListOf<Food?>()

    fun submitList(foods: List<Food?>?) {
        foods?.let {
            this.foods.apply {
                clear()
                addAll(it)
                notifyItemRangeInserted(0, it.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        food?.let { holder.bind(it) }
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {
            binding.apply {
                imgPhoto.loadRounded(food.photo)
                tvName.text = food.name.capitalize(Locale.current)
                tvMeasure.text = itemView.context.resources.getString(
                    R.string.measure_with_weight,
                    food.servingQuantity.toDecimal(),
                    food.servingUnit,
                    food.servingWeightGrams.toDecimal()
                )
                tvCalories.text = itemView.context.resources.getString(
                    R.string.calories,
                    food.nfCalories.toDecimal()
                )

                itemView.setOnClickListener {
                    navigateToDetails(food)
                }
            }
        }

        private fun navigateToDetails(food: Food) {
            itemView.findNavController().navigate(
                NavMainDirections.actionGlobalToDetails(food)
            )
        }
    }
}