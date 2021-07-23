package com.ariefzuhri.gizee.core.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.databinding.ItemFoodBinding
import com.ariefzuhri.gizee.details.DetailsActivity
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.Constants.Companion.EXTRA_FOOD
import org.apache.commons.lang3.StringUtils

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private val foods = arrayListOf<FoodEntity?>()

    fun submitList(foods: List<FoodEntity?>?) {
        with(this.foods) {
            clear()
            foods?.let { addAll(it) }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        food?.let { holder.bind(it) }
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: FoodEntity) {
            AppUtils.loadImage(itemView.context, binding.imgPhoto, food.photo)
            binding.tvName.text = StringUtils.capitalize(food.name)
            binding.tvMeasure.text = itemView.context.resources.getString(
                R.string.measure_with_weight,
                AppUtils.getDecimalFormat(food.servingQty),
                food.servingUnit,
                AppUtils.getDecimalFormat(food.servingWeightGrams)
            )
            binding.tvCalories.text =
                itemView.context.resources.getString(
                    R.string.calories,
                    AppUtils.getDecimalFormat(food.nfCalories)
                )
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailsActivity::class.java)
                intent.putExtra(EXTRA_FOOD, food)
                itemView.context.startActivity(intent)
            }
        }
    }
}