package com.ariefzuhri.gizee.home.searchresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.data.source.local.entity.FoodEntity
import com.ariefzuhri.gizee.core.data.source.local.entity.HistoryEntity
import com.ariefzuhri.gizee.core.data.source.remote.network.ApiResponse
import com.ariefzuhri.gizee.core.ui.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.ui.viewmodel.ViewModelFactory
import com.ariefzuhri.gizee.core.utils.AppUtils
import com.ariefzuhri.gizee.core.utils.DataMapper
import com.ariefzuhri.gizee.core.utils.ShimmerHelper
import com.ariefzuhri.gizee.databinding.FragmentSearchResultBinding
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsFragment
import com.ariefzuhri.gizee.home.HomeViewModel

private const val ARG_QUERY = "query"

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var query: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            query = it.getString(ARG_QUERY) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        val TAG: String = SearchResultFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(query: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_QUERY, query)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        val shimmer = ShimmerHelper(binding.shimmer, binding.layoutResult, binding.layoutEmpty)
        shimmer.show()

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(requireActivity(), factory)[HomeViewModel::class.java]
        viewModel.insertHistory(HistoryEntity(query))
        viewModel.searchFoodsByNaturalLanguage(query).observe(requireActivity()) { result ->
            if (result != null) {
                when (result) {
                    is ApiResponse.Success -> {
                        val foods = DataMapper.mapResponseToEntities(result.data)
                        populateAdapter(foods)
                        populateNutritionFacts(foods)
                        shimmer.hide(foods.isNullOrEmpty())
                    }
                    is ApiResponse.Error -> {
                        AppUtils.showToast(context, result.errorMessage)
                    }
                    is ApiResponse.Empty -> shimmer.hide(true)
                }
            }
        }
    }

    private fun populateAdapter(foods: List<FoodEntity?>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = FoodAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(foods)
    }

    private fun populateNutritionFacts(foods: List<FoodEntity>) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        var fragment = childFragmentManager.findFragmentByTag(NutritionFactsFragment.TAG)
        if (fragment == null) {
            fragment = NutritionFactsFragment.newInstance(foods)
            fragmentTransaction.add(binding.container.id, fragment, NutritionFactsFragment.TAG)
        }
        fragmentTransaction.commit()
    }
}