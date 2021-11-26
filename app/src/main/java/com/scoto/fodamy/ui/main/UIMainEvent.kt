package com.scoto.fodamy.ui.main

import androidx.navigation.NavDirections

sealed class UIMainEvent {
    data class NavigateTo(val directions: NavDirections? = null, val message: String? = null) :
        UIMainEvent()

    data class ShowMessage(val message: String) : UIMainEvent()
}
