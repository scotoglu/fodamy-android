package com.scoto.fodamy.ui.favorites.recipe_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeDetailsBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding, RecipeDetailsViewModel>(
    R.layout.fragment_recipe_details
) {
    override fun getViewModel(): Class<RecipeDetailsViewModel> = RecipeDetailsViewModel::class.java
}