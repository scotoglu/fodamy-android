package com.scoto.fodamy.ui.home.pages

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentPagesOfTabBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.HomeFragmentDirections
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagesOfTab : BaseFragment<FragmentPagesOfTabBinding>(
    R.layout.fragment_pages_of_tab
) {

    private val viewModel: PagesOfTabViewModel by viewModels()
    private lateinit var recipeAdapter: RecipesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        adapterLoadStateListener()

        viewModel.recipes.observe(viewLifecycleOwner, {
            recipeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        adapterItemClicks()
    }

    private fun adapterItemClicks() {
        recipeAdapter.onItemClicked = {
            navigateTo(HomeFragmentDirections.actionHomeFragmentToRecipeFlow(it))
        }
    }

    private fun adapterLoadStateListener() {
        recipeAdapter.addLoadStateListener { loadState ->
            binding.apply {

                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvRecipes.isVisible = loadState.source.refresh is LoadState.NotLoading


            }
        }
    }

    private fun setupRecyclerView() {
        recipeAdapter = RecipesAdapter()
        binding.adapter = recipeAdapter
        binding.rvRecipes.apply {
            setHasFixedSize(true)
            adapter = recipeAdapter
        }
    }


    companion object {
        private const val TAG = "PagesOfTab"
    }
}
