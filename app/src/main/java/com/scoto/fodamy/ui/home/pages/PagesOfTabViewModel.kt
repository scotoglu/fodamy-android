package com.scoto.fodamy.ui.home.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.ui.home.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagesOfTabViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val state: SavedStateHandle
) : BaseViewModel() {

    private var _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: LiveData<PagingData<Recipe>> get() = _recipes

    init {
//        when (state.get<String>(REQUEST_TYPE)) {
//            FROM_EDITOR_CHOICE -> getEditorChoices()
//            FROM_LAST_ADDED -> getLastAdded()
//        }
    }

    fun getEditorChoices() = viewModelScope.launch {
        sendRequest(success = {
            recipeRepository.getEditorChoiceRecipes().cachedIn(viewModelScope).collect {
                _recipes.value = it
            }
        })
    }

    fun getLastAdded() = viewModelScope.launch {
        sendRequest(success = {
            recipeRepository.getLastAdded().cachedIn(viewModelScope).collect {
                _recipes.value = it
            }
        })
    }

    fun toRecipeDetails(recipe: Recipe) {
        navigate(HomeFragmentDirections.actionHomeFragmentToRecipeFlow(recipe))
    }

    companion object {
        private const val EDITOR_CHOICE = "editor_choice"
        private const val LAST_ADDED = "last_added"
        private const val FETCHED = "fetched"
    }
}
