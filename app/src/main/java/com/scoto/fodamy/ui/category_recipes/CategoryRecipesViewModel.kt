package com.scoto.fodamy.ui.category_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.util.FROM_RECIPES_BY_CATEGORY
import com.scoto.fodamy.util.paging_sources.RecipePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryRecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val id: Int = savedStateHandle.get<Int>(CATEGORY_ID) ?: 0
    val title: String = savedStateHandle.get<String>(CATEGORY_TITLE) ?: ""

    private val _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: LiveData<PagingData<Recipe>> get() = _recipes

    val event: SingleLiveEvent<CategoryEvent> = SingleLiveEvent()

    init {
        getRecipesByCategory()
        isLogin()
    }

    private fun isLogin() = viewModelScope.launch {
        event.value = CategoryEvent.IsLogin(dataStoreManager.isLogin())
    }

    private fun getRecipesByCategory() = viewModelScope.launch {

        sendRequest(
            success = {
                val pager = Pager(
                    config = pageConfig,
                    pagingSourceFactory = {
                        RecipePagingSource(
                            recipeRepository,
                            FROM_RECIPES_BY_CATEGORY,
                            id
                        )
                    }
                ).flow
                pager.cachedIn(viewModelScope).collect {
                    _recipes.value = it
                }
            }
        )
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                success = {
                    val res = authRepository.logout()
                    showMessage(res.message)
                }
            )
        }
    }

    fun toRecipeDetails(recipe: Recipe) {
        navigate(
            CategoryRecipesFragmentDirections.actionCategoryRecipesFragmentToRecipeFlow(
                recipe
            )
        )
    }

    companion object {
        private val pageConfig = PagingConfig(
            pageSize = 24,
            maxSize = 100,
            enablePlaceholders = false
        )
        private const val CATEGORY_ID = "CategoryId"
        private const val CATEGORY_TITLE = "CategoryTitle"
    }
}
