package com.scoto.fodamy.ui.favorites.recipe_details

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val navArgs: RecipeDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRecipeById()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        //TODO("complete share functionality")
        //TODO("move the logic to viewModel")
        binding.customToolbar.apply {
            getEndIcon().onClick { view.snackbar("Recipe will be shared.") }
            getBackIcon().onClick { findNavController().popBackStack() }
        }

        viewModel.recipe.observe(viewLifecycleOwner, {
            binding.apply {
                recipe = it
            }
        })

        eventObserver()
        followButton()
    }

    private fun followButton() {
        binding.includeUser.btnFollow.apply {
            visibility = View.VISIBLE
            onClick {
                viewModel.onFollowClick()
            }
        }
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UIRecipeEvent.IsFollowing -> {
                    setupFollowButton(true)
                }
                is UIRecipeEvent.CommentData -> {
                    binding.includeComment.comment = event.comment
                }
                is UIRecipeEvent.NavigateTo -> {
                    navigateTo(event.directions)
                }
                is UIRecipeEvent.ShowMessage.ErrorMessage -> {
                    binding.root.snackbar(event.message)
                }
                is UIRecipeEvent.ShowMessage.SuccessMessage -> {
                    setupFollowButton(event.isFollowing)
                    binding.root.snackbar(event.message)

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