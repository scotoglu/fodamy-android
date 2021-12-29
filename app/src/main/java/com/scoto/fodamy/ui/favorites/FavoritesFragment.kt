package com.scoto.fodamy.ui.favorites

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentFavoritesBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.favorites.adapter.CategoryPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(R.layout.fragment_favorites) {

    private lateinit var categoryAdapter: CategoryPagingAdapter

    override fun registerObservables() {
        super.registerObservables()
        categoryObserver()
        viewStateObserver()
    }

    private fun categoryObserver() {
        viewModel.categories.observe(viewLifecycleOwner, {
            categoryAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    override fun addItemClicks() {
        super.addItemClicks()
        categoryAdapter.onItemClicked = { category ->
            viewModel.toSeeAll(category)
        }
        categoryAdapter.onChildItemClicked = { recipe ->
            viewModel.toRecipeDetail(recipe)
        }
    }

    override fun addAdapterLoadStateListener() {
        super.addAdapterLoadStateListener()
        categoryAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvCategories.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }
    }

    override fun initViews() {
        super.initViews()
        categoryAdapter = CategoryPagingAdapter()
        binding.adapter = categoryAdapter
        binding.rvCategories.apply {
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
    }

    private fun viewStateObserver() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            when (viewState) {
                is FavoritesViewState.IsLogin -> binding.customToolbar.setEndIconVisibility(viewState.isLogin)
                is FavoritesViewState.Success -> {
                    binding.root.snackbar(viewState.message)
                    binding.customToolbar.setEndIconVisibility(false)
                }
            }
        })
    }

    companion object {
        private const val TAG = "FavoritesFragment"
    }
}
