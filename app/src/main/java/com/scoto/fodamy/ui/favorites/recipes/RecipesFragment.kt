package com.scoto.fodamy.ui.favorites.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.favorites.recipe_details.RecipeDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : BaseFragment<FragmentRecipeBinding, RecipesViewModel>(
    R.layout.fragment_recipe
) {
    override fun getViewModel(): Class<RecipesViewModel> = RecipesViewModel::class.java
}