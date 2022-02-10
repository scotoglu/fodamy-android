package com.scoto.fodamy.ui.home.last_added

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentLastAddedBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.01.2022 at 16:27
 */
class LastAddedFragment() :
    BaseFragment<FragmentLastAddedBinding, LastAddedViewModel>(R.layout.fragment_last_added) {

    private lateinit var recipesAdapter: RecipesAdapter
    override val isSharedViewModel: Boolean = true

    override fun registerObservables() {
        super.registerObservables()
        viewModel.recipes.observe(viewLifecycleOwner) {
            recipesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun initViews() {
        super.initViews()
        recipesAdapter = RecipesAdapter()
        binding.adapter = recipesAdapter
        binding.rvRecipes.apply {
            setHasFixedSize(true)
            adapter = recipesAdapter
        }
    }

    override fun addItemClicks() {
        super.addItemClicks()
        recipesAdapter.onItemClicked = {
            viewModel.toRecipeDetails(it)
        }
    }

    override fun addAdapterLoadStateListener() {
        super.addAdapterLoadStateListener()
        recipesAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvRecipes.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }
    }
}
