package com.scoto.fodamy.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.Category
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.network.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _categories: MutableLiveData<PagingData<Category>> = MutableLiveData()
    val categories: LiveData<PagingData<Category>> get() = _categories

    val event: SingleLiveEvent<UIFavoritesEvent> = SingleLiveEvent()

    init {
        getCategories()
    }

    fun onLogoutClick() = viewModelScope.launch {

        if (dataStoreManager.isLogin()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Error -> {
                    event.value =
                        UIFavoritesEvent.ShowMessage.Error(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    event.value = UIFavoritesEvent.ShowMessage.Success(response.data.message)
                }
            }
        } else {
            event.value =
                UIFavoritesEvent.NavigateTo(FavoritesFragmentDirections.actionFavoritesFragmentToLoginFlow2())
        }
    }

    private fun getCategories() = viewModelScope.launch {
        recipeRepository.getCategoriesWithRecipes().cachedIn(viewModelScope).collect { pagingData ->
            _categories.value = pagingData.filter { category ->
                category.recipes?.size!! > 0
            }
        }
    }

    suspend fun isLoginLiveData(): LiveData<String> =
        dataStoreManager.isLoginLiveData()
}
