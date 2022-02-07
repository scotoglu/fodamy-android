package com.scoto.fodamy.ui.recipe_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeDetailsBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.util.KEY_UNFOLLOW_COMPLETED
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment :
    BaseFragment<FragmentRecipeDetailsBinding, RecipeDetailsViewModel>(
        R.layout.fragment_recipe_details
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(com.scoto.fodamy.util.DIALOG_ACTION) { _, bundle ->
            val result = bundle.get(KEY_UNFOLLOW_COMPLETED)
            if (result as Boolean) {
                viewModel.getRecipeById()
            }
        }
    }

    companion object {
        private const val DIALOG_ACTION = "DialogAction"
        private const val UNFOLLOW = "Unfollow"
    }
}
