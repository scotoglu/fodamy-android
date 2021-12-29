package com.scoto.fodamy.ui.category_recipes

sealed class CategoryViewState {
    data class Success(val message: String) : CategoryViewState()
    data class IsLogin(val isLogin: Boolean) : CategoryViewState()
}
