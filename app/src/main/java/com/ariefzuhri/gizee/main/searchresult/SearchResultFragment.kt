package com.ariefzuhri.gizee.main.searchresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.R
import com.ariefzuhri.gizee.core.data.repository.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.ui.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.utils.TAG
import com.ariefzuhri.gizee.core.utils.ShimmerHelper
import com.ariefzuhri.gizee.core.utils.isNetworkAvailable
import com.ariefzuhri.gizee.core.utils.showToast
import com.ariefzuhri.gizee.databinding.FragmentSearchResultBinding
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultFragment : Fragment() {

    private val args: SearchResultFragmentArgs by navArgs()

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var query: String
    private val viewModel: SearchResultViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        query = args.query
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        observeSearchResult()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeSearchResult() {
        val shimmer = ShimmerHelper(
            binding.layoutPlaceholder.root,
            binding.layoutEmpty.root,
            binding.recyclerView, binding.fcvNutritionFacts
        )

        viewModel.performQuery(query)
        viewModel.searchResult.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> shimmer.show()
                    is Resource.Success -> {
                        val history = result.data
                        populateAdapter(history?.foods)
                        populateNutritionFacts(history?.foods)
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

    private fun populateNutritionFacts(foods: List<Food?>?) {
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
    }
}