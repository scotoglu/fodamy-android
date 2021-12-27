package com.scoto.fodamy.ui.profile

import androidx.navigation.NavDirections

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.12.2021 at 15:05
 */
sealed class UIProfileEvent {
    object ShowMessage {
        data class SuccessMessage(val message: String) : UIProfileEvent()
        data class ErrorMessage(val message: String) : UIProfileEvent()
    }

    data class NavigateTo(val actionId: Int? = null, val direction: NavDirections? = null) :
        UIProfileEvent()

    data class IsLogin(val isLogin: Boolean) : UIProfileEvent()
}
