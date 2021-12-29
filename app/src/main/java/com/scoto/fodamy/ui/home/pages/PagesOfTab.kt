package com.scoto.fodamy.ui.home.pages

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentPagesOfTabBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagesOfTab : BaseFragment<FragmentPagesOfTabBinding, PagesOfTabViewModel>(
    R.layout.fragment_pages_of_tab
) {

    private lateinit var recipeAdapter: RecipesAdapter

    override fun registerObservables() {
        super.registerObservables()
        viewModel.recipes.observe(viewLifecycleOwner, {
            recipeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    override fun initViews() {
        super.initViews()
        recipeAdapter = RecipesAdapter()
        binding.adapter = recipeAdapter
        binding.rvRecipes.apply {
            setHasFixedSize(true)
            adapter = recipeAdapter
        }
    }

    override fun addItemClicks() {
        super.addItemClicks()
        recipeAdapter.onItemClicked = {
            viewModel.toRecipeDetails(it)
        }
    }

    override fun addAdapterLoadStateListener() {
        super.addAdapterLoadStateListener()
        recipeAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvRecipes.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }
    }


    companion object {
        private const val TAG = "PagesOfTab"
    }
}
