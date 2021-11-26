package com.scoto.fodamy.ui.auth

import androidx.navigation.NavDirections

sealed class UIAuthEvent {
    data class NavigateTo(val directions: NavDirections?, val message: String? = null) :
        UIAuthEvent()
}
