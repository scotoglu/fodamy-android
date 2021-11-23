package com.scoto.fodamy.ui.favorites.recipe_details

import android.util.Log
import androidx.lifecycle.*
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.models.Comment
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
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var _comment: SingleLiveEvent<Comment?> = SingleLiveEvent()
    val comment: SingleLiveEvent<Comment?> get() = _comment


    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String?> get() = _error

    val noComment: MutableLiveData<Boolean> = MutableLiveData(false)

    val recipe = savedStateHandle.get<Recipe>("RECIPE")


    init {
        getRecipeComments()
    }

    private fun getRecipeComments() = viewModelScope.launch {
        if (recipe != null) {
            when (val response = recipeRepository.getFirstComment(recipe.id)) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "Response : ${response.data}")
                    _comment.value = response.data
                }
                is NetworkResult.Error.HttpError -> {
                    _error.value = response.message.toString()
                    Log.d(TAG, "HttpError:${response.message} ")
                }
                is NetworkResult.Error.IOError -> {
                    _error.value = response.message.toString()
                    Log.d(TAG, "IOError:${response.message} ")
                }
                is NetworkResult.Error.OutOfIndexError -> {
                    noComment.value = true
                }
            }
        }
    }

    fun follow() = viewModelScope.launch {
        val followedUserId: Int = recipe?.user?.id!!
        when (val response = userRepository.followUser(followedUserId)) {
            is NetworkResult.Success -> {
                Log.d(TAG, "follow: ${response.data.message}")
            }

            is NetworkResult.Error.HttpError -> {
                Log.d(TAG, "follow: Error ${response.message}")
            }
            is NetworkResult.Error.IOError -> {
                Log.d(TAG, "follow: Error ${response.message}")
            }

        }

    }

    companion object {
        private const val TAG = "RecipeDetailsViewModel"
    }

}
