package com.scoto.fodamy.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentFavoritesBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.favorites.adapter.CategoryPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(R.layout.fragment_favorites) {

    private lateinit var categoryAdapter: CategoryPagingAdapter

    override val isSharedViewModel: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.isLogin()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun registerObservables() {
        super.registerObservables()
        categoryObserver()
        eventObserver()
    }

    private fun categoryObserver() {
        viewModel.categories.observe(viewLifecycleOwner) {
            categoryAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun addItemClicks() {
        super.addItemClicks()
        categoryAdapter.apply {
            onItemClicked = { category ->
                viewModel.toSeeAll(category)
            }
            onChildItemClicked = { recipe ->
                viewModel.toRecipeDetail(recipe)
            }
        }
        binding.swipeLayout.apply {
            setOnRefreshListener {
                isRefreshing = true
                categoryAdapter.refresh()
            }
        }
    }

    override fun addAdapterLoadStateListener() {
        super.addAdapterLoadStateListener()
        categoryAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvCategories.isVisible = loadState.source.refresh is LoadState.NotLoading
                swipeLayout.isRefreshing = loadState.source.refresh !is LoadState.NotLoading
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

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            val logoutIconVisibility: Boolean = when (event) {
                is FavoritesEvent.IsLogin -> event.isLogin
                FavoritesEvent.Success -> false
            }
            binding.customToolbar.setEndIconVisibility(logoutIconVisibility)
        }
    }
}
