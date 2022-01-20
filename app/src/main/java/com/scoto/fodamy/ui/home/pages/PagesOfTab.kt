package com.scoto.fodamy.ui.home.pages

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentPagesOfTabBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import com.scoto.fodamy.ui.home.adapter.ViewPagerAdapter.Companion.REQUEST_TYPE
import com.scoto.fodamy.util.FROM_EDITOR_CHOICE
import com.scoto.fodamy.util.FROM_LAST_ADDED
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagesOfTab : BaseFragment<FragmentPagesOfTabBinding, PagesOfTabViewModel>(
    R.layout.fragment_pages_of_tab
) {

    private lateinit var recipeAdapter: RecipesAdapter
//    override val isSharedViewModel: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (arguments?.get(REQUEST_TYPE)) {
            FROM_EDITOR_CHOICE -> viewModel.getEditorChoices()
            FROM_LAST_ADDED -> viewModel.getLastAdded()
        }
    }

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
}
