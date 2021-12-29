package com.scoto.fodamy.ui.recipe_details

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeDetailsBinding
import com.scoto.fodamy.ext.colorStateList
import com.scoto.fodamy.ext.getColorBy
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment :
    BaseFragment<FragmentRecipeDetailsBinding, RecipeDetailsViewModel>(
        R.layout.fragment_recipe_details
    ) {

    override fun registerObservables() {
        super.registerObservables()
        recipeObservable()
        commentObservable()
        dialogActionObserver()
    }

    private fun commentObservable() {
        viewModel.comment.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.includeComment.isEmptyComment = false
                binding.includeComment.comment = it
            } else {
                binding.includeComment.isEmptyComment = true
            }
        }

    }

    private fun recipeObservable() {
        viewModel.recipe.observe(viewLifecycleOwner, {
            binding.apply {
                recipe = it
                ivLike.imageTintList = requireContext().colorStateList(
                    if (it.isLiked) R.color.red else R.color.black
                )
                includeUser.btnFollow.apply {
                    setupFollowButton(it.user.isFollowing)
                }

                ivLike.onClick { viewModel?.onLike() }
                includeUser.btnFollow.isVisible = true
                includeUser.btnFollow.onClick { viewModel?.onFollow() }
            }
        })
    }

    private fun dialogActionObserver() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("ACTION")
            ?.observe(viewLifecycleOwner) {
                if (it.equals("unfollow")) {
                    viewModel.unfollow()
                }
            }
    }

    //TODO("xml, selector usage")
    private fun setupFollowButton(isFollowing: Boolean) {
        binding.includeUser.btnFollow.apply {
            if (isFollowing) {
                text = getString(R.string.btn_followed)
                backgroundTintList = requireContext().colorStateList(R.color.red)
                setTextColor(requireContext().getColorBy(R.color.white))
            } else {
                text = getString(R.string.btn_unfollowed)
                backgroundTintList = requireContext().colorStateList(R.color.white)
                setTextColor(requireContext().getColorBy(R.color.red))
            }
        }
    }

    companion object {
        private const val TAG = "RecipeDetailsFragment"
    }
}
