package com.ariefzuhri.gizee.core.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.core.R
import com.ariefzuhri.gizee.core.databinding.ItemFoodBinding
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.utils.EXTRA_FOOD
import com.ariefzuhri.gizee.core.utils.bindLoadImage
import com.ariefzuhri.gizee.core.utils.toDecimal

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
        holder.bind(food)
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food?) {
            binding.apply {
                bindLoadImage(imgPhoto, food?.photo)
                tvName.text = food?.name?.capitalize(Locale.current)
                tvMeasure.text = itemView.context.resources.getString(
                    R.string.measure_with_weight,
                    food?.servingQty.toDecimal(),
                    food?.servingUnit,
                    food?.servingWeightGrams.toDecimal()
                )
                tvCalories.text = itemView.context.resources.getString(
                    R.string.calories,
                    food?.nfCalories.toDecimal()
                )
                itemView.setOnClickListener {
                    val intent = Intent(
                        itemView.context,
                        Class.forName("com.ariefzuhri.gizee.details.DetailsActivity")
                    )
                    intent.putExtra(EXTRA_FOOD, food)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}