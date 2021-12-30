package com.scoto.fodamy.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.Category
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.network.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
) : BaseViewModel() {

    private val _categories: MutableLiveData<PagingData<Category>> = MutableLiveData()
    val categories: LiveData<PagingData<Category>> get() = _categories

    val event: SingleLiveEvent<FavoritesEvent> = SingleLiveEvent()

    init {
        getCategories()
        isLogin()
    }

    fun isLogin() = viewModelScope.launch {
        event.value = FavoritesEvent.IsLogin(dataStoreManager.isLogin())
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Error -> {
                    showMessage(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    event.value = FavoritesEvent.Success(response.data.message)
                }
            }
        }
    }

    private fun getCategories() = viewModelScope.launch {
        recipeRepository.getCategoriesWithRecipes().cachedIn(viewModelScope).collect { pagingData ->
            _categories.value = pagingData.filter { category ->
                category.recipes?.size!! > 0
            }
        }
    }

    fun toSeeAll(category: Category) {
        navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToCategoryRecipesFragment(
                category.id, category.name
            )
        )
    }

    fun toRecipeDetail(recipe: Recipe) {
        navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFlow2(
                recipe
            )
        )
    }
}
