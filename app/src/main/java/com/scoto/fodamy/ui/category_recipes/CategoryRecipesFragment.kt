package com.scoto.fodamy.ui.category_recipes

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCategoryRecipeBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import com.scoto.fodamy.ui.home.pages.RecipeItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryRecipesFragment : BaseFragment<FragmentCategoryRecipeBinding>(
    R.layout.fragment_category_recipe
), RecipeItemClickListener {

    private lateinit var categoryRecipesAdapter: RecipesAdapter
    private val viewModel: CategoryRecipesViewModel by viewModels()
    private val args: CategoryRecipesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRvCategoryRecipes()


        viewModel.recipes.observe(viewLifecycleOwner, {
            categoryRecipesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })



        categoryRecipesAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressbar.isVisible = loadState.source.refresh is LoadState.Loading
                tvLoading.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }

        eventObserver()
        endIconObserver()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.customToolbar.setTitle(args.categoryTitle)

    }

    private fun setupRvCategoryRecipes() {
        categoryRecipesAdapter = RecipesAdapter(this)
        binding.rvCategoryRecipes.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = categoryRecipesAdapter
        }
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UICategoryEvent.BackTo -> findNavController().popBackStack()
                is UICategoryEvent.NavigateTo -> navigateTo(event.directions)
                is UICategoryEvent.OpenDialog -> findNavController().navigate(event.actionId)
                is UICategoryEvent.ShowMessage -> binding.root.snackbar(event.message)

            }
        })

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

    private fun navigateTo(directions: NavDirections) {
        val navController = findNavController()
        navController.navigate(directions)
    }

    override fun onItemClicked(recipe: Recipe) {
        navigateTo(
            CategoryRecipesFragmentDirections.actionCategoryRecipesFragmentToRecipeFlow(
                recipe
            )
        )


    }

    companion object {
        private const val TAG = "CategoryRecipesFragment"
    }
}