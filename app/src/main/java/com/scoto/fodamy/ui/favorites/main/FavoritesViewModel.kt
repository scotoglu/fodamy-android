package com.scoto.fodamy.ui.favorites.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.network.models.Category
import com.scoto.fodamy.network.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _categories: MutableLiveData<PagingData<Category>> = MutableLiveData()
    val categories: LiveData<PagingData<Category>> get() = _categories


    init {
        getCategories()
    }

    fun logout() = viewModelScope.launch {

    }

    private fun getCategories() = viewModelScope.launch {
        recipeRepository.getCategoriesWithRecipes().cachedIn(viewModelScope).collect {
            _categories.value = it
        }
    }

}
