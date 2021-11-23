package com.scoto.fodamy.ui.favorites.recipe_details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeDetailsBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ext.toast
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding>(
    R.layout.fragment_recipe_details
) {
    private val viewModel: RecipeDetailsViewModel by viewModels()
    private val navArgs: RecipeDetailsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passedRecipe: Recipe = navArgs.RECIPE

        binding.apply {
            lifecycleOwner = this@RecipeDetailsFragment
            viewModel = viewModel
            recipe = passedRecipe
            includeUser.user = passedRecipe.user
        }

        binding.includeUser.btnFollow.isVisible = true

        setToolbarEndIcon()
        setFirstComment()
        binding.includeUser.btnFollow.onClick {
            viewModel.follow()
        }
    }

    private fun setFirstComment() {
        viewModel.comment.observe(viewLifecycleOwner, { comment ->
            comment?.let {
                binding.includeComment.comment = it
            }
        })
    }

    private fun setToolbarEndIcon() {
        val endIcon = requireActivity().findViewById<ImageView>(R.id.toolbar_iv_end_icon)
        endIcon.setImageResource(R.drawable.ic_share)
        endIcon.onClick {
            //Share recipe
            requireContext().toast("Recipe will be shared")
        }
    }

    companion object {
        private const val TAG = "RecipeDetailsFragment"
    }
}