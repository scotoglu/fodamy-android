package com.scoto.fodamy.ui.favorites.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentFavoritesBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.favorites.adapter.CategoryPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryPagingAdapter()
        setupRvCategory()
        viewModel.categories.observe(viewLifecycleOwner, {
            categoryAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        categoryAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressbar.isVisible = loadState.source.refresh is LoadState.Loading
                tvLoading.isVisible = loadState.source.refresh is LoadState.Loading
                rvCategories.isVisible = loadState.source.refresh is LoadState.NotLoading
            }

        }
    }

    private fun setupRvCategory() {
        binding.rvCategories.apply {
            setHasFixedSize(true)
            adapter = categoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
}