package com.scoto.fodamy.ui.favorites.recipes

import androidx.fragment.app.viewModels
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : BaseFragment<FragmentRecipeBinding>(
    R.layout.fragment_recipe
) {

    private val viewModel: RecipesViewModel by viewModels()
}