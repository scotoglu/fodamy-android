package com.scoto.fodamy.ui.favorites.recipe_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _event: SingleLiveEvent<UIRecipeEvent> = SingleLiveEvent()//remove line
    val event: SingleLiveEvent<UIRecipeEvent> get() = _event

    private val _recipe = savedStateHandle.getLiveData<Recipe>("RECIPE")
    val recipe: LiveData<Recipe> get() = _recipe

    private var _isFollowing: Boolean = _recipe.value?.user?.isFollowing!!
    private var recipeId: Int = _recipe.value?.id!!
    private var _isLiked: Boolean = _recipe.value?.isLiked ?: false

    init {
        getRecipeComments()

    }


    fun getRecipeById() = viewModelScope.launch {
        _recipe.value?.let {
            when (val response = recipeRepository.getRecipeById(it.id)) {
                is NetworkResponse.Success -> {
//                    _event.value = UIRecipeEvent.RecipeData(response.data)
                    if (response.data.user.isFollowing) _event.value = UIRecipeEvent.IsFollowing
                    savedStateHandle.set("RECIPE", response.data)
                }
                is NetworkResponse.Error -> {
                    _event.value =
                        UIRecipeEvent.ShowMessage.ErrorMessage(response.exception.handleException())
                }
            }
        }
    }


    private fun getRecipeComments() = viewModelScope.launch {
        _recipe.value?.let {
            when (val response = recipeRepository.getFirstComment(it.id)) {
                is NetworkResponse.Success -> {
                    _event.value = UIRecipeEvent.CommentData(response.data)
                }
                is NetworkResponse.Error -> {
                    _event.value =
                        UIRecipeEvent.ShowMessage.ErrorMessage(response.exception.handleException())
                }
            }
        }
    }

    fun onImageSlider() {
        _event.value = UIRecipeEvent.NavigateTo(
            RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToImagePopupFragment(
                ImageList(recipe.value!!.images)
            )
        )
    }

    fun onCommentAddClick() {
        _event.value = UIRecipeEvent.NavigateTo(
            RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToCommentsFragment(recipeId)
        )
    }

    fun onFollowClick() = viewModelScope.launch {
        if (!dataStoreManager.isLogin()) {
            _event.value =
                UIRecipeEvent.NavigateTo(RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToAuthDialog())
        } else {
            _recipe.value?.let {
                val followedUserId = it.user.id
                if (_isFollowing) unFollow(followedUserId)
                else follow(followedUserId)

            }
        }
    }

    fun onShareClick() {
        Log.d(TAG, "onShareClick: Recipe will be shared")
    }

    fun onBackClick() {
        _event.value = UIRecipeEvent.BackTo
    }

    fun onLikeClick() {
        Log.d(TAG, "onLikeClick: OnLikeClicked")
        if (_isLiked) unLike()
        else like()
    }

    private fun like() {

    }

    private fun unLike() {

    }

    private fun unFollow(followedUserId: Int) = viewModelScope.launch {
        when (val response = userRepository.unFollowUser(followedUserId)) {
            is NetworkResponse.Success -> {
                _event.value =
                    UIRecipeEvent.ShowMessage.SuccessMessage(response.data.message, false)
                _isFollowing = false
            }
            is NetworkResponse.Error -> {
                _event.value =
                    UIRecipeEvent.ShowMessage.ErrorMessage(response.exception.handleException())
            }
        }

    }

    private fun follow(followedUserId: Int) =
        viewModelScope.launch {
            when (val response = userRepository.followUser(followedUserId)) {
                is NetworkResponse.Success -> {
                    _event.value =
                        UIRecipeEvent.ShowMessage.SuccessMessage(response.data.message, true)
                    _isFollowing = true
                }
                is NetworkResponse.Error -> {
                    _event.value =
                        UIRecipeEvent.ShowMessage.ErrorMessage(response.exception.handleException())
                }

            }
        }


    companion object {
        private const val TAG = "RecipeDetailsViewModel"
    }

}
