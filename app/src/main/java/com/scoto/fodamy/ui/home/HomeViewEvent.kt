package com.scoto.fodamy.ui.home

sealed class HomeViewEvent {
    data class IsLogin(val isLogin: Boolean) : HomeViewEvent()
    object Success : HomeViewEvent()
}
