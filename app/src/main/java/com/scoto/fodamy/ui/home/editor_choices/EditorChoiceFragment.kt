package com.scoto.fodamy.ui.home.editor_choices

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentEditorChoiceBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.01.2022 at 16:26
 */
@AndroidEntryPoint
class EditorChoiceFragment :
    BaseFragment<FragmentEditorChoiceBinding, EditorChoiceViewModel>(R.layout.fragment_editor_choice) {
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
