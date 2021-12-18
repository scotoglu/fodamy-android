package com.scoto.fodamy.ui.category_recipes

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.network.repositories.RecipeRepository
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

    ) : ViewModel() {


    private val id: Int = savedStateHandle.get<Int>("CategoryId") ?: 0

    private val _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: LiveData<PagingData<Recipe>> get() = _recipes

    val event: SingleLiveEvent<UICategoryEvent> = SingleLiveEvent()

    init {
        getRecipesByCategory()
    }


    private fun getRecipesByCategory() = viewModelScope.launch {
        recipeRepository.getRecipesByCategory(id).cachedIn(viewModelScope).collect {
            _recipes.value = it
        }
    }


    fun onBackClick() {
        event.value = UICategoryEvent.BackTo
    }

    fun onLogoutClick() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Error -> event.value =
                    UICategoryEvent.ShowMessage(response.exception.handleException())
                is NetworkResponse.Success -> {
                    event.value = UICategoryEvent.ShowMessage(response.data.message)
                }
            }

        } else {
            event.value = UICategoryEvent.OpenDialog(R.id.action_global_authDialog)
        }
    }
    suspend fun isLoginLiveData(): LiveData<String> =
        dataStoreManager.isLoginLiveData()

    companion object {
        private const val TAG = "CategoryRecipesViewMode"
    }
}
