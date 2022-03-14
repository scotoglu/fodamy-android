package com.scoto.fodamy.ui.add_recipe.drafts

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentDraftsBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
@AndroidEntryPoint
class RecipesDraftsFragment : BaseFragment<FragmentDraftsBinding, DraftsViewModel>(
    R.layout.fragment_drafts
)
