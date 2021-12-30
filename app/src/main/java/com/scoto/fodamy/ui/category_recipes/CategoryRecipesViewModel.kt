package com.scoto.fodamy.ui.category_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.network.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
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

    private val id: Int = savedStateHandle.get<Int>("CategoryId") ?: 0
    val title: String = savedStateHandle.get<String>("CategoryTitle") ?: ""

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
        recipeRepository.getRecipesByCategory(id).cachedIn(viewModelScope).collect {
            _recipes.value = it
        }
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Error ->
                    showMessage(response.exception.handleException())
                is NetworkResponse.Success -> {
                    event.value = CategoryEvent.Success(response.data.message)
                }
            }
        }
    }

    fun toRecipeDetails(recipe: Recipe) {
        navigate(
            CategoryRecipesFragmentDirections.actionCategoryRecipesFragmentToRecipeFlow(
                recipe
            )
        )
    }

    suspend fun isLoginLiveData(): LiveData<String> =
        dataStoreManager.isLoginLiveData()

    companion object {
        private const val TAG = "CategoryRecipesViewMode"
    }
}
