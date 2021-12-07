package com.scoto.fodamy.ui.favorites.recipe_details
/*
* like/unLike and follow/unFollow operations send request per each call.
* To update of like count we should update the recipe also.
* So each successful request send cost two request 1-Operation (like,unlike vs) request 2-getRecipeById request for update recipe.
*
* TODO("Fix this")
*
* */
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.ImageList
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.repositories.RecipeRepository
import com.scoto.fodamy.network.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    val event: SingleLiveEvent<UIRecipeEvent> = SingleLiveEvent()

    private val _recipe = savedStateHandle.getLiveData<Recipe>("RECIPE")
    val recipe: LiveData<Recipe> get() = _recipe

    private val recipeId: Int = _recipe.value?.id!!
    private val followedUserId: Int = _recipe.value?.user?.id ?: 1

    init {
        getRecipeById()
        getRecipeComments()
    }


    private fun getRecipeById() = viewModelScope.launch {
        _recipe.value?.let {
            when (val response = recipeRepository.getRecipeById(it.id)) {
                is NetworkResponse.Success -> {
                    savedStateHandle.set("RECIPE", response.data)
                }
                is NetworkResponse.Error -> {
                    event.value =
                        UIRecipeEvent.ShowMessage(response.exception.handleException())
                }
            }
        }
    }


    private fun getRecipeComments() = viewModelScope.launch {
        _recipe.value?.let {
            when (val response = recipeRepository.getFirstComment(it.id)) {
                is NetworkResponse.Success -> {
                    event.value = UIRecipeEvent.CommentData(response.data)
                }
                is NetworkResponse.Error -> {
                    event.value =
                        UIRecipeEvent.ShowMessage(response.exception.handleException())
                }
            }
        }
    }

    fun onImageSlider() {
        event.value = UIRecipeEvent.NavigateTo(
            RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToImagePopupFragment(
                ImageList(recipe.value!!.images)
            )
        )
    }

    fun onCommentAddClick() {
        event.value = UIRecipeEvent.NavigateTo(
            RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToCommentsFragment(recipeId)
        )
    }

    fun onShareClick() {
        Log.d(TAG, "onShareClick: Recipe will be shared")
    }

    fun onBackClick() {
        event.value = UIRecipeEvent.BackTo
    }

    fun onLikeClick() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            recipe.value!!.let {
                if (it.isLiked) dislike() else like()
            }
        } else {
            event.value =
                UIRecipeEvent.OpenDialog(R.id.action_global_authDialog)
        }
    }

    private fun like() = viewModelScope.launch {
        when (val response = recipeRepository.likeRecipe(recipeId)) {
            is NetworkResponse.Error -> {
                event.value =
                    UIRecipeEvent.ShowMessage(response.exception.handleException())
            }
            is NetworkResponse.Success -> {
                event.value = UIRecipeEvent.ShowMessage(response.data.message)
                getRecipeById()
            }
        }
    }

    private fun dislike() = viewModelScope.launch {
        when (val response = recipeRepository.dislikeRecipe(recipeId)) {
            is NetworkResponse.Error -> {
                event.value =
                    UIRecipeEvent.ShowMessage(response.exception.handleException())
            }
            is NetworkResponse.Success -> {
                event.value = UIRecipeEvent.ShowMessage(response.data.message)
                getRecipeById()
            }
        }
    }

    fun onFollowClick() = viewModelScope.launch {
        if (!dataStoreManager.isLogin()) {
            event.value =
                UIRecipeEvent.OpenDialog(R.id.action_global_authDialog)
        } else {
            _recipe.value?.let {
                if (it.user.isFollowing) unFollow() else follow()

            }
        }
    }

    private fun unFollow() = viewModelScope.launch {
        when (val response = userRepository.unFollowUser(followedUserId)) {
            is NetworkResponse.Success -> {
                event.value =
                    UIRecipeEvent.ShowMessage(response.data.message)
                getRecipeById()
            }
            is NetworkResponse.Error -> {
                event.value =
                    UIRecipeEvent.ShowMessage(response.exception.handleException())
            }
        }
    }

    private fun follow() =
        viewModelScope.launch {
            when (val response = userRepository.followUser(followedUserId)) {
                is NetworkResponse.Success -> {
                    event.value =
                        UIRecipeEvent.ShowMessage(response.data.message)
                    getRecipeById()
                }
                is NetworkResponse.Error -> {
                    event.value =
                        UIRecipeEvent.ShowMessage(response.exception.handleException())
                }

            }
        }


    companion object {
        private const val TAG = "RecipeDetailsViewModel"
    }

}
