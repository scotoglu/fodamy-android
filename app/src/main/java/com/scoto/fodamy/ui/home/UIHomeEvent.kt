package com.scoto.fodamy.ui.home

import androidx.navigation.NavDirections

sealed class UIHomeEvent {
    data class NavigateTo(val direction: NavDirections) : UIHomeEvent()
    object ShowMessage : UIHomeEvent() {
        data class Success(val message: String) : UIHomeEvent()
        data class Error(val message: String) : UIHomeEvent()
    }
}