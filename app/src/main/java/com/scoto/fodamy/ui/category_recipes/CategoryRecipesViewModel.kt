package com.scoto.fodamy.ui.category_recipes

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Recipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.usecase.LogoutUseCase
import com.scoto.domain.usecase.params.NoParams
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
    private val dataStoreManager: DataStoreManager,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private var categoryId: Int = -1
    var title: String = ""

    private val _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: LiveData<PagingData<Recipe>> get() = _recipes

    val event: SingleLiveEvent<CategoryEvent> = SingleLiveEvent()

    init {
        isLogin()
    }

    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        with(CategoryRecipesFragmentArgs.fromBundle(bundle)) {
            this@CategoryRecipesViewModel.categoryId = categoryId
            this@CategoryRecipesViewModel.title = categoryTitle
        }
        getRecipesByCategory()
    }

    private fun isLogin() = viewModelScope.launch {
        event.value = CategoryEvent.IsLogin(dataStoreManager.isLogin())
    }

    private fun getRecipesByCategory() {
        sendRequest(
            request = {
                Pager(
                    config = pageConfig,
                    pagingSourceFactory = {
                        RecipePagingSource(
                            recipeRepository,
                            FROM_RECIPES_BY_CATEGORY,
                            categoryId
                        )
                    }
                ).flow
            },
            success = { pagingData ->
                pagingData.cachedIn(viewModelScope).collect {
                    _recipes.value = it
                }
            }
        )
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                loading = true,
                request = { logoutUseCase.invoke(NoParams(Any())) },
                success = {
                    event.value = CategoryEvent.Success
                    showMessage(it.message)
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
    }
}
