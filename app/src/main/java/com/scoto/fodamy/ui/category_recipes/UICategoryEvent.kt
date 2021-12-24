package com.scoto.fodamy.ui.category_recipes

import androidx.navigation.NavDirections
import com.scoto.fodamy.ui.home.UIHomeEvent

sealed class UICategoryEvent {
    data class NavigateTo(val directions: NavDirections) : UICategoryEvent()
    object ShowMessage {
        data class Success(val message: String) : UICategoryEvent()
        data class Error(val message: String) : UICategoryEvent()
    }
    data class IsLogin(val isLogin: Boolean) : UICategoryEvent()
    object BackTo : UICategoryEvent()
}
