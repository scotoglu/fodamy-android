package com.scoto.fodamy.ui.recipe_details

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRecipeDetailsBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding>(
    R.layout.fragment_recipe_details
) {
    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.recipe.observe(viewLifecycleOwner, {
            binding.apply {
                recipe = it
                ivLike.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        requireContext(),
                        if (it.isLiked) R.color.red else R.color.black
                    )
                )
                includeUser.btnFollow.apply {
                    setupFollowButton(it.user.isFollowing)
                }
            }
        })

        binding.apply {
            ivLike.onClick { viewModel?.onLikeClick() }

            includeUser.btnFollow.isVisible = true
            includeUser.btnFollow.onClick { viewModel?.onFollowClick() }
        }



        eventObserver()
        dialogActionObserver()

    }

    private fun dialogActionObserver() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("ACTION")
            ?.observe(viewLifecycleOwner) {
                if (it.equals("unfollow")) {
                    viewModel.unFollow()
                }
            }
    }


    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UIRecipeEvent.CommentData -> {
                    if (event.comment == null) {
                        binding.includeComment.isEmptyComment = true
                    } else {
                        binding.includeComment.isEmptyComment = false
                        binding.includeComment.comment = event.comment
                    }
                }
                is UIRecipeEvent.NavigateTo -> {
                    navigateTo(event.directions)
                }
                is UIRecipeEvent.ShowMessage -> {
                    binding.root.snackbar(event.message)
                }
                is UIRecipeEvent.BackTo -> {
                    findNavController().popBackStack()
                }
                is UIRecipeEvent.OpenDialog -> {
                    findNavController().navigate(event.actionId)
                }
            }
        })
    }

    private fun navigateTo(direction: NavDirections) {
        val navController = findNavController()
        navController.navigate(direction)
    }

    private fun setupFollowButton(isFollowing: Boolean) {
        binding.includeUser.btnFollow.apply {
            if (isFollowing) {
                text = getString(R.string.btn_followed)
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
                setTextColor(ContextCompat.getColor(context, R.color.white))
            } else {
                binding.includeUser.btnFollow.apply {
                    text = getString(R.string.btn_unfollowed)
                    backgroundTintList =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
                    setTextColor(ContextCompat.getColor(context, R.color.red))
                }
            }
        }
    }

    companion object {
        private const val TAG = "RecipeDetailsFragment"
    }
}