package com.scoto.fodamy.ui.category_recipes

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCategoryRecipeBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryRecipesFragment :
    BaseFragment<FragmentCategoryRecipeBinding, CategoryRecipesViewModel>(
        R.layout.fragment_category_recipe
    ) {

    private lateinit var categoryRecipesAdapter: RecipesAdapter

    override fun registerObservables() {
        super.registerObservables()
        recipeObserver()
        eventObserver()
    }

    private fun recipeObserver() {
        viewModel.recipes.observe(viewLifecycleOwner, {
            categoryRecipesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    override fun addItemClicks() {
        super.addItemClicks()
        categoryRecipesAdapter.onItemClicked = { recipe ->
            viewModel.toRecipeDetails(recipe)
        }
    }

    override fun addAdapterLoadStateListener() {
        super.addAdapterLoadStateListener()
        categoryRecipesAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvCategoryRecipes.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }
    }

    override fun initViews() {
        super.initViews()
        categoryRecipesAdapter = RecipesAdapter()
        binding.adapter = categoryRecipesAdapter
        binding.rvCategoryRecipes.apply {
            setHasFixedSize(true)
            adapter = categoryRecipesAdapter
        }
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { even ->
            when (even) {
                is CategoryEvent.IsLogin -> binding.customToolbar.setEndIconVisibility(even.isLogin)
                is CategoryEvent.Success -> {
                    binding.root.snackbar(even.message)
                    binding.customToolbar.setEndIconVisibility(false)
                }
            }
        })
    }

    companion object {
        private const val TAG = "CategoryRecipesFragment"
    }
}
