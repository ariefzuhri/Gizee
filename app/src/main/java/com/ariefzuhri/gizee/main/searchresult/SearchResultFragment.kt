package com.ariefzuhri.gizee.main.searchresult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefzuhri.gizee.core.data.Resource
import com.ariefzuhri.gizee.core.domain.model.Food
import com.ariefzuhri.gizee.core.ui.adapter.FoodAdapter
import com.ariefzuhri.gizee.core.utils.ShimmerHelper
import com.ariefzuhri.gizee.databinding.FragmentSearchResultBinding
import com.ariefzuhri.gizee.nutritionfacts.NutritionFactsFragment
import com.ariefzuhri.gizee.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_QUERY = "query"

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
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

        val viewModel: MainViewModel by viewModel()
        viewModel.performQuery(query)
        viewModel.getFoodsByNaturalLanguage.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> shimmer.show()
                    is Resource.Success -> {
                        val foods = result.data
                        populateAdapter(foods)
                        populateNutritionFacts(foods)
                        shimmer.hide(foods.isNullOrEmpty())
                    }
                    is Resource.Error -> shimmer.hide(true)
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
            fragmentTransaction.add(binding.container.id, fragment, NutritionFactsFragment.TAG)
        }
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}