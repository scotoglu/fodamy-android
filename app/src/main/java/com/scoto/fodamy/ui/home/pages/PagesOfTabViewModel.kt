package com.scoto.fodamy.ui.home.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.ui.home.HomeFragmentDirections
import com.scoto.fodamy.ui.home.adapter.ViewPagerAdapter.Companion.REQUEST_TYPE
import com.scoto.fodamy.util.FROM_EDITOR_CHOICE
import com.scoto.fodamy.util.FROM_LAST_ADDED
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
        when (state.get<String>(REQUEST_TYPE)) {
            FROM_EDITOR_CHOICE -> getEditorChoices()
            FROM_LAST_ADDED -> getLastAdded()
        }
    }

    private fun getEditorChoices() = viewModelScope.launch {
        recipeRepository.getEditorChoiceRecipes().cachedIn(viewModelScope).collect {
            _recipes.value = it
        }
    }

    private fun getLastAdded() = viewModelScope.launch {
        recipeRepository.getLastAdded().cachedIn(viewModelScope).collect {
            _recipes.value = it
        }
    }

    fun toRecipeDetails(recipe: Recipe) {
        navigate(HomeFragmentDirections.actionHomeFragmentToRecipeFlow(recipe))
    }

    companion object {
        private const val TAG = "PagesOfTabViewModel"
    }
}
