package com.ariefzuhri.gizee.favorites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.ui.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.utils.gone
import com.ariefzuhri.gizee.core.utils.isNotEmpty
import com.ariefzuhri.gizee.favorites.databinding.ActivityFavoritesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoritesModule)

        val viewModel: FavoritesViewModel by viewModel()
        viewModel.getFavorites.observe(this) { foods -> populateAdapter(foods) }
    }

    private fun populateAdapter(foods: List<Food>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = FoodAdapter()
        adapter.submitList(foods)
        binding.recyclerView.adapter = adapter
        binding.viewEmpty.root.gone(adapter.isNotEmpty())
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(favoritesModule)
    }
}