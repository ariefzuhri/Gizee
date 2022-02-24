package com.ariefzuhri.gizee.feature.favorites.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.common.util.gone
import com.ariefzuhri.gizee.core.common.util.isNotEmpty
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.feature.favorites.databinding.FragmentFavoritesBinding
import com.ariefzuhri.gizee.feature.favorites.di.FavoritesModule
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val module by lazy { FavoritesModule }
    private val viewModel: FavoritesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        module.load()

        observeFavorites()
    }

    private fun observeFavorites() {
        viewModel.getFavorites.observe(viewLifecycleOwner) { foods ->
            populateAdapter(foods)
        }
    }

    private fun populateAdapter(foods: List<Food>?) {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = FoodAdapter()
            adapter.submitList(foods)
            recyclerView.adapter = adapter
            layoutEmpty.root.gone(adapter.isNotEmpty())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        module.unload()
    }
}