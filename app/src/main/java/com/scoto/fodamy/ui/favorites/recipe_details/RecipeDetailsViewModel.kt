package com.scoto.fodamy.ui.favorites.recipe_details

import androidx.lifecycle.*
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
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

    private val _event: MutableLiveData<UIRecipeEvent> = MutableLiveData()
    val event: LiveData<UIRecipeEvent> get() = _event

    //    val recipe = savedStateHandle.get<Recipe>("RECIPE")
    private val _recipe = savedStateHandle.getLiveData<Recipe>("RECIPE")
    val recipe: LiveData<Recipe> = MutableLiveData(_recipe.value)

    init {
        getRecipeComments()
    }


    fun getRecipeById() = viewModelScope.launch {
        _recipe.value?.let {
            when (val response = recipeRepository.getRecipeById(it.id)) {
                is NetworkResponse.Success -> {
//                    _event.value = UIRecipeEvent.RecipeData(response.data)
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

    fun follow() = viewModelScope.launch {
        val token = dataStoreManager.getToken()
        if (token.isBlank()) {
            _event.value =
                UIRecipeEvent.NavigateTo(RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToAuthDialog())
        } else {
            val followedUserId: Int = _recipe.value?.user?.id!!
            when (val response = userRepository.followUser(followedUserId)) {
                is NetworkResponse.Success -> {
                    _event.value = UIRecipeEvent.ShowMessage.SuccessMessage(response.data.message)
                }
                is NetworkResponse.Error -> {
                    _event.value =
                        UIRecipeEvent.ShowMessage.ErrorMessage(response.exception.handleException())
                }

            }
        }

    }

    companion object {
        private const val TAG = "RecipeDetailsViewModel"
    }

}
