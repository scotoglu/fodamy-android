package com.scoto.fodamy.ui.home.editor_choices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.ui.home.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.01.2022 at 16:28
 */
@HiltViewModel
class EditorChoiceViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    private val _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: LiveData<PagingData<Recipe>> get() = _recipes

    init {
        getRecipes()
    }

    private fun getRecipes() {
        sendRequest(
            request = {
                recipeRepository.getEditorChoicePaging()
            },
            success = {
                it.cachedIn(viewModelScope).collect { pagingData ->
                    _recipes.value = pagingData
                }
            }
        )
    }

    fun toRecipeDetails(recipe: Recipe) {
        navigate(HomeFragmentDirections.actionHomeFragmentToRecipeFlow(recipe))
    }
}
