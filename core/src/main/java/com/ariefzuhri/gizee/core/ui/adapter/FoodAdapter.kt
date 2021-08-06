package com.ariefzuhri.gizee.core.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefzuhri.gizee.core.R
import com.ariefzuhri.gizee.core.databinding.ItemFoodBinding
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.Constants.EXTRA_FOOD
import com.ariefzuhri.gizee.core.utils.ViewBinding
import org.apache.commons.lang3.StringUtils

class FoodAdapter : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private val foods = arrayListOf<Food?>()

    fun submitList(foods: List<Food?>?) {
        foods?.let {
            with(this.foods) {
                clear()
                addAll(it)
                notifyItemRangeInserted(0, it.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            ViewBinding.bindLoadImage(binding.imgPhoto, food?.photo)
            binding.tvName.text = StringUtils.capitalize(food?.name)
            binding.tvMeasure.text = itemView.context.resources.getString(
                R.string.measure_with_weight,
                AppUtils.formatToDecimal(food?.servingQty),
                food?.servingUnit,
                AppUtils.formatToDecimal(food?.servingWeightGrams)
            )
            binding.tvCalories.text =
                itemView.context.resources.getString(
                    R.string.calories,
                    AppUtils.formatToDecimal(food?.nfCalories)
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