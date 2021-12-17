package com.scoto.fodamy.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentFavoritesBinding
import com.scoto.fodamy.ext.snackbar
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

        endIconObserver()
        eventObserver()
        categoryAdapter = CategoryPagingAdapter()
        setupRvCategory()


        viewModel.categories.observe(viewLifecycleOwner, {
            categoryAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })


        adapterItemClicks()
        adapterLoadStateListener()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

//        binding.root.setOnApplyWindowInsetsListener { _, insets ->
//            val navHeight = WindowInsetsCompat.toWindowInsetsCompat(insets)
//                .getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
//            (binding.root.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin = navHeight
//            insets
//        }
    }

    private fun adapterItemClicks() {
        categoryAdapter.onItemClicked = {
            navigateTo(
                FavoritesFragmentDirections.actionFavoritesFragmentToCategoryRecipesFragment(
                    it.id, it.name
                )
            )
        }
        categoryAdapter.onChildItemClicked = {
            navigateTo(
                FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFlow2(
                    it
                )
            )
        }
    }

    private fun adapterLoadStateListener() {
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


    companion object {
        private const val TAG = "FavoritesFragment"
    }
}