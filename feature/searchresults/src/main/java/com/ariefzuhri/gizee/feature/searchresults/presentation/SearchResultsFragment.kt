package com.ariefzuhri.gizee.feature.searchresults.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.feature.searchresults.databinding.FragmentSearchResultsBinding
import com.ariefzuhri.gizee.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.common.R
import com.ariefzuhri.gizee.core.common.wrapper.Resource
import com.ariefzuhri.gizee.core.common.util.ShimmerHelper
import com.ariefzuhri.gizee.core.common.util.TAG
import com.ariefzuhri.gizee.core.common.util.isNetworkAvailable
import com.ariefzuhri.gizee.core.common.util.showToast
import com.ariefzuhri.gizee.core.database.domain.model.Food
import com.ariefzuhri.gizee.feature.nutritionfacts.presentation.NutritionFactsFragment
import com.ariefzuhri.gizee.feature.searchresults.di.SearchResultsModule
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultsFragment : Fragment() {

    private val args: SearchResultsFragmentArgs by navArgs()

    private var _binding: FragmentSearchResultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var query: String
    private val module by lazy { SearchResultsModule }
    private val viewModel: SearchResultsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        module.load()

        query = args.query
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        observeSearchResults()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeSearchResults() {
        val shimmer = ShimmerHelper(
            binding.layoutPlaceholder.root,
            binding.layoutEmpty.root,
            binding.recyclerView, binding.fcvNutritionFacts
        )

        viewModel.performQuery(query)
        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> shimmer.show()
                    is Resource.Success -> {
                        val history = result.data
                        populateAdapter(history?.foods)
                        populateNutritionFacts(history?.foods?.toCollection(ArrayList()))
                        shimmer.hide(history?.foods.isNullOrEmpty())
                    }
                    is Resource.Error -> {
                        if (!isNetworkAvailable()) {
                            activity.showToast(result.message)
                            activity.showToast(R.string.toast_error_connection)
                        }
                        shimmer.hide(true)
                    }
                }
            }
        }
    }

    private fun populateAdapter(foods: List<Food?>?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = FoodAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(foods)
    }

    private fun populateNutritionFacts(foods: ArrayList<Food?>?) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        var fragment = childFragmentManager.findFragmentByTag(NutritionFactsFragment.TAG)
        if (fragment == null) {
            fragment = NutritionFactsFragment.newInstance(foods)
            fragmentTransaction.add(
                binding.fcvNutritionFacts.id,
                fragment, NutritionFactsFragment.TAG
            )
        }
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        module.unload()
    }
}