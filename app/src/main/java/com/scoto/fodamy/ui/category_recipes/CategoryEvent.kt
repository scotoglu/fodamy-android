package com.scoto.fodamy.ui.category_recipes

sealed class CategoryEvent {
    data class Success(val message: String) : CategoryEvent()
    data class IsLogin(val isLogin: Boolean) : CategoryEvent()
}
