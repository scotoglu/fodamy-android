package com.scoto.fodamy.ui.favorites.recipe_details

import androidx.fragment.app.viewModels
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeDetailsBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding>(
    R.layout.fragment_recipe_details
) {
    private val viewModel: RecipeDetailsViewModel by viewModels()
}