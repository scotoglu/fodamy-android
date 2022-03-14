package com.scoto.fodamy.ui.add_recipe

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentAddRecipeBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.03.2022
 */
@AndroidEntryPoint
class AddRecipeFragment : BaseFragment<FragmentAddRecipeBinding, AddRecipeViewModel>(
    R.layout.fragment_add_recipe
)
