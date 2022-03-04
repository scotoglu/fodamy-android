package com.scoto.fodamy.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Category
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.usecase.LogoutUseCase
import com.scoto.domain.usecase.params.NoParams
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager,
    private val logoutUseCase: LogoutUseCase
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
            sendRequest(
                loading = true,
                request = { logoutUseCase.invoke(NoParams(Any())) },
                success = {
                    event.value = FavoritesEvent.Success
                    showMessage(it.message)
                }
            )
        }
    }

    private fun getCategories() {
        sendRequest(
            request = {
                recipeRepository.getCategoriesPaging()
            },
            success = {
                viewModelScope.launch {
                    it.cachedIn(viewModelScope).collect {
                        _categories.value = it
                    }
                }
            }
        )
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

    companion object {
        private val pageConfig = PagingConfig(
            pageSize = 24,
            maxSize = 100,
            enablePlaceholders = false
        )
    }
}
