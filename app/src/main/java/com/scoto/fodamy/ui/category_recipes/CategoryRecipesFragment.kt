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
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCategoryRecipeBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CategoryRecipesFragment : BaseFragment<FragmentCategoryRecipeBinding>(
    R.layout.fragment_category_recipe
) {


    private val viewModel: CategoryRecipesViewModel by viewModels()
    private val args: CategoryRecipesFragmentArgs by navArgs()

    private lateinit var categoryRecipesAdapter: RecipesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        setupRvCategoryRecipes()


        viewModel.recipes.observe(viewLifecycleOwner, {
            categoryRecipesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        categoryRecipesAdapter.onItemClicked = {
            navigateTo(
                CategoryRecipesFragmentDirections.actionCategoryRecipesFragmentToRecipeFlow(
                    it
                )
            )
        }

        eventObserver()
        endIconObserver()
        adapterLoadStateListener()
        setToolbarTitle()

    }

    private fun setToolbarTitle() {
        binding.customToolbar.setTitle(
            args.categoryTitle
                .uppercase(Locale.forLanguageTag("tr"))
        )

    }

    private fun adapterLoadStateListener() {
        categoryRecipesAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressbar.isVisible = loadState.source.refresh is LoadState.Loading
                tvLoading.isVisible = loadState.source.refresh is LoadState.Loading
            }
        }
    }

    private fun setupRvCategoryRecipes() {
        categoryRecipesAdapter = RecipesAdapter()
        binding.rvCategoryRecipes.apply {
            setHasFixedSize(true)
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

    companion object {
        private const val TAG = "CategoryRecipesFragment"
    }
}