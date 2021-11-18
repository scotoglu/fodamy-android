package com.scoto.fodamy.ui.home.pages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentPagesOfTabBinding
import com.scoto.fodamy.ui.base.BaseFragment
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


        recipeAdapter = RecipesAdapter()

        setupRv()

        viewModel.recipes.observe(viewLifecycleOwner, {
            recipeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

    }


    private fun setupRv() {
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

    private fun navigateTo() {
        //navigateTo recipeDetails
    }

    companion object {
        private const val TAG = "PagesOfTab"
        private val actionToRecipeDetails =
            PagesOfTabDirections.actionPagesOfTabToRecipeDetailsFragment()
    }

}