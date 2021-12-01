package com.scoto.fodamy.ui.favorites.recipe_details

import androidx.lifecycle.*
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
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

    private val _event: SingleLiveEvent<UIRecipeEvent> = SingleLiveEvent()
    val event: SingleLiveEvent<UIRecipeEvent> get() = _event

    private val _recipe = savedStateHandle.getLiveData<Recipe>("RECIPE")
    val recipe: LiveData<Recipe> = MutableLiveData(_recipe.value)

    private var _isFollowing: Boolean = _recipe.value?.user?.isFollowing!!
    private var recipeId: Int = _recipe.value?.id!!

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
