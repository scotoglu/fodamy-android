package com.scoto.fodamy.ui.home.pages

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagesOfTabViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {


    private var _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: MutableLiveData<PagingData<Recipe>> get() = _recipes

    init {
        getEditorChoices()
    }

    private fun getEditorChoices() = viewModelScope.launch {
        val response = recipeRepository.getEditorChoiceRecipes().cachedIn(viewModelScope)
        _recipes.value = response.value
    }

    companion object {
        private const val TAG = "PagesOfTabViewModel"
    }
}
