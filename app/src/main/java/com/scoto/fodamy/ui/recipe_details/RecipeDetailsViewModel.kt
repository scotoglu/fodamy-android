package com.scoto.fodamy.ui.recipe_details
/*
* like/unLike and follow/unFollow operations send request per each call.
* To update the like count we should update the recipe also.
* So each successful request send two request, 1-Operation (like,unlike vs) request 2-getRecipeById request for update recipe.
*
* TODO("Fix this")
*
* */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.scoto.domain.models.Comment
import com.scoto.domain.models.ImageList
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.repositories.UserRepository
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    private val _recipe = savedStateHandle.getLiveData<Recipe>(RECIPE)
    val recipe: LiveData<Recipe> get() = _recipe

    private val _comment: MutableLiveData<Comment?> = MutableLiveData()
    val comment: LiveData<Comment?> get() = _comment

    private val recipeId: Int = _recipe.value?.id ?: 1
    private val followedUserId: Int = _recipe.value?.user?.id ?: 1

    init {
        getRecipeById()
        getRecipeComments()
    }

    private fun getRecipeById() {
        sendRequest(
            request = { recipeRepository.getRecipeById(recipeId) },
            success = {
                savedStateHandle.set(RECIPE, it)
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
                ImageList(recipe.value!!.images)
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
            recipe.value!!.let {
                if (it.isLiked) dislike() else like()
            }
        } else {
            openDialog(R.id.action_global_authDialog)
        }
    }

    private fun like() {
        sendRequest(
            request = { recipeRepository.likeRecipe(recipeId) },
            success = {
                showMessage(it.message)
                getRecipeById()
            }
        )
    }

    private fun dislike() {
        sendRequest(
            request = { recipeRepository.dislikeRecipe(recipeId) },
            success = {
                showMessage(it.message)
                getRecipeById()
            }
        )
    }

    fun onFollow() = viewModelScope.launch {
        if (recipe.value?.user?.isFollowing == true) {
            navigate(
                RecipeDetailsFragmentDirections
                    .actionRecipeDetailsFragmentToUnfollowDialog()
            )
        } else {
            if (!dataStoreManager.isLogin()) {
                openDialog(R.id.action_global_authDialog)
            } else {
                _recipe.value?.let {
                    follow()
                }
            }
        }
    }

    fun unfollow() {
        sendRequest(
            request = { userRepository.unFollowUser(followedUserId) },
            success = {
                showMessage(it.message)
                getRecipeById()
            }
        )
    }

    private fun follow() {
        sendRequest(
            request = { userRepository.followUser(followedUserId) },
            success = {
                showMessage(it.message)
                getRecipeById()
            }
        )
    }

    companion object {
        private const val RECIPE = "RECIPE"
    }
}
