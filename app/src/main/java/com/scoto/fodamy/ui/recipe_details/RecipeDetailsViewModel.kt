package com.scoto.fodamy.ui.recipe_details

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.domain.models.Comment
import com.scoto.domain.models.ImageList
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.usecase.DislikeUseCase
import com.scoto.domain.usecase.FollowUseCase
import com.scoto.domain.usecase.LikeUseCase
import com.scoto.domain.usecase.params.FollowParams
import com.scoto.domain.usecase.params.RecipeParams
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager,
    private val likeUseCase: LikeUseCase,
    private val dislikeUseCase: DislikeUseCase,
    private val followUseCase: FollowUseCase,
) : BaseViewModel() {

    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> get() = _recipe

    private val _comment: MutableLiveData<Comment?> = MutableLiveData()
    val comment: LiveData<Comment?> get() = _comment

    private var recipeId: Int = -1
    private var followedUserId: Int = -1

    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        with(RecipeDetailsFragmentArgs.fromBundle(bundle)) {
            this@RecipeDetailsViewModel._recipe.value = recipe
            this@RecipeDetailsViewModel.recipeId = recipe.id
            this@RecipeDetailsViewModel.followedUserId = recipe.user.id
        }
        getRecipeById()
        getRecipeComments()
    }

    fun getRecipeById() {
        sendRequest(
            loading = true,
            request = { recipeRepository.getRecipeById(recipeId) },
            success = {
                _recipe.value = it
            }
        )
    }

    private fun getRecipeComments() {
        sendRequest(
            request = { recipeRepository.getFirstComment(recipeId) },
            success = {
                _comment.value = it
            }
        )
    }

    fun onImageSlider() {
        navigate(
            RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToImagePopupFragment(
                ImageList(recipe.value?.images!!)
            )
        )
    }

    fun onCommentAdd() {
        navigate(
            RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToCommentsFragment(recipeId)
        )
    }

    fun onShare() {
        showMessageWithRes(R.string.text_sharing)
    }

    fun onLike() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            recipe.value?.let {
                if (it.isLiked) dislike() else like()
            }
        } else {
            openNavigationDilog(R.id.action_global_authDialog)
        }
    }

    private fun like() {
        sendRequest(
            request = { likeUseCase.invoke(RecipeParams(recipeId)) },
            success = {
                showMessageWithRes(R.string.success_like)
                _recipe.value = it
            }
        )
    }

    private fun dislike() {
        sendRequest(
            request = { dislikeUseCase.invoke(RecipeParams(recipeId)) },
            success = {
                showMessageWithRes(R.string.success_dislike)
                _recipe.value = it
            }
        )
    }

    fun onFollow() = viewModelScope.launch {
        if (recipe.value?.user?.isFollowing == true) {
            navigate(
                RecipeDetailsFragmentDirections
                    .actionRecipeDetailsFragmentToUnfollowDialog(followedUserId)
            )
        } else {
            if (!dataStoreManager.isLogin()) {
                openNavigationDilog(R.id.action_global_authDialog)
            } else {
                _recipe.value?.let {
                    follow()
                }
            }
        }
    }

    private fun follow() {
        sendRequest(
            request = { followUseCase.invoke(FollowParams(followedUserId, recipeId)) },
            success = {
                showMessageWithRes(R.string.success_follow)
                _recipe.value = it
            }
        )
    }

    companion object {
        private const val RECIPE = "RECIPE"
    }
}
