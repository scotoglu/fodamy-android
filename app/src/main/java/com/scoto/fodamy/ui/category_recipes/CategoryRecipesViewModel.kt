package com.scoto.fodamy.ui.category_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
//        recipeRepository.getRecipesByCategory(id).cachedIn(viewModelScope).collect {
//            _recipes.value = it
//        }
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
//            when (val response = authRepository.logout()) {
//                is NetworkResponse.Error ->
//                    showMessage(response.exception.handleException())
//                is NetworkResponse.Success -> {
//                    event.value = CategoryEvent.Success(response.data.message)
//                }
//            }
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
        private const val CATEGORY_ID = "CategoryId"
        private const val CATEGORY_TITLE = "CategoryTitle"
    }
}
