package com.scoto.fodamy.ui.category_recipes

sealed class CategoryEvent {
    object Success : CategoryEvent()
    data class IsLogin(val isLogin: Boolean) : CategoryEvent()
}
