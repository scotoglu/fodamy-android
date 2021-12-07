package com.scoto.fodamy.ui.home.pages

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentPagesOfTabBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.HomeFragmentDirections
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagesOfTab : BaseFragment<FragmentPagesOfTabBinding>(
    R.layout.fragment_pages_of_tab
), RecipeItemClickListener {

    private val viewModel: PagesOfTabViewModel by viewModels()
    private lateinit var recipeAdapter: RecipesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initAdapterAndAddStateListener()
        setupRecyclerView()

        viewModel.recipes.observe(viewLifecycleOwner, {
            recipeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

    }


    private fun initAdapterAndAddStateListener() {

        recipeAdapter = RecipesAdapter(this)

        recipeAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressbar.isVisible = loadState.source.refresh is LoadState.Loading
                rvRecipes.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvStateError.isVisible = loadState.source.refresh is LoadState.Error


                btnRetry.onClick {
                    recipeAdapter.retry()
                }

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    recipeAdapter.itemCount < 1
                ) {
                    rvRecipes.isVisible = false
                    tvEmptyList.isVisible = true
                } else {
                    tvEmptyList.isVisible = false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvRecipes.apply {
            setHasFixedSize(true)
            adapter = recipeAdapter
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
        }
    }

    /*
    * ViewPager hosted by HomeFragment.And PagesOfTab() fragments host on home.
    * Calling details screen from PagesOfTabDirections cause error. CurrentDestination is HomeFragment.
    * To handle this error, navigate to details screen using HomeFragmentDirections also add action home to recipeDetails.
    * Not from PagesOfTab to recipeDetails.
    * */
    override fun onItemClicked(recipe: Recipe) {
        val navController = findNavController()
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToRecipeFlow(
                recipe
            )
        )
    }

    companion object {
        private const val TAG = "PagesOfTab"
    }

}