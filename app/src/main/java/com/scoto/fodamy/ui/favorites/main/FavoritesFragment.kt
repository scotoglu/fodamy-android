package com.scoto.fodamy.ui.favorites.main

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentFavoritesBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.network.models.Category
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.favorites.adapter.CategoryPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites), CategoryClickListener {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        endIconObserver()
        eventObserver()

        categoryAdapter = CategoryPagingAdapter(this)
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupRvCategory() {
        binding.rvCategories.apply {
            setHasFixedSize(true)
            adapter = categoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun endIconObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isLoginLiveData().observe(viewLifecycleOwner, {
                if (it.isNotBlank()) {
                    val drawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_logout_2)
                    binding.customToolbar.setEndIcon(drawable)
                }
            })
        }

    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UIFavoritesEvent.NavigateTo -> navigateTo(event.directions)
                is UIFavoritesEvent.ShowMessage -> binding.root.snackbar(event.message)
            }
        })
    }

    private fun navigateTo(directions: NavDirections) {
        val navController = findNavController()
        navController.navigate(directions)
    }

    override fun onItemClicked(item: Category) {
        navigateTo(
            FavoritesFragmentDirections.actionFavoritesFragmentToCategoryRecipesFragment(
                item.id, item.name!!
            )
        )
    }

    companion object {
        private const val TAG = "FavoritesFragment"
    }
}