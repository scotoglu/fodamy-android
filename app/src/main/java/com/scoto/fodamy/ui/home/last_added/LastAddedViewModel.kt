package com.scoto.fodamy.ui.home.last_added

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.ui.home.HomeFragmentDirections
import com.scoto.fodamy.util.FROM_LAST_ADDED
import com.scoto.fodamy.util.paging_sources.RecipePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.01.2022 at 16:28
 */
@HiltViewModel
class LastAddedViewModel @Inject constructor(
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
                Pager(
                    config = pageConfig,
                    pagingSourceFactory = {
                        RecipePagingSource(
                            recipeRepository,
                            FROM_LAST_ADDED, null
                        )
                    }
                ).flow
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

    companion object {
        private val pageConfig = PagingConfig(
            pageSize = 24,
            maxSize = 100,
            enablePlaceholders = false
        )
    }
}
