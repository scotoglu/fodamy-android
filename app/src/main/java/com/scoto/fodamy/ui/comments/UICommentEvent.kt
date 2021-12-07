package com.scoto.fodamy.ui.comments

import androidx.navigation.NavDirections

sealed class UICommentEvent {
    data class NavigateTo(val directions: NavDirections) : UICommentEvent()
    data class OpenDialog(val actionId: Int) : UICommentEvent()
    object ShowMessage {
        data class SuccessMessage(val message: String) : UICommentEvent()
        data class ErrorMessage(val message: String) : UICommentEvent()
    }
}
