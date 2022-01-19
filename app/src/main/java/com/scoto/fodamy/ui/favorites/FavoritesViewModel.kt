package com.scoto.fodamy.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.scoto.domain.models.Category
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
//            when (val response = authRepository.logout()) {
//                is NetworkResponse.Error -> {
//                    showMessage(response.exception.handleException())
//                }
//                is NetworkResponse.Success -> {
//                    event.value = FavoritesEvent.Success(response.data.message)
//                }
//            }
        }
    }

    private fun getCategories() = viewModelScope.launch {
//        recipeRepository.getCategoriesWithRecipes().cachedIn(viewModelScope).collect { pagingData ->
//            _categories.value = pagingData.filter { category ->
//                category.recipes?.size!! > 0
//            }
//        }
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
