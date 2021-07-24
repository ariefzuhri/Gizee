package com.ariefzuhri.gizee.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.databinding.ActivityFavoritesBinding
import com.ariefzuhri.gizee.core.ui.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.ui.viewmodel.ViewModelFactory

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[FavoritesViewModel::class.java]
        viewModel.getFavorites.observe(this) { foods -> populateAdapter(foods) }
    }

    private fun populateAdapter(foods: List<Food>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = FoodAdapter()
        adapter.submitList(foods)
        binding.recyclerView.adapter = adapter

        if (adapter.itemCount > 0) binding.layoutEmpty.visibility = View.INVISIBLE
        else binding.layoutEmpty.visibility = View.VISIBLE
    }
}