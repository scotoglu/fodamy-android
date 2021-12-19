package com.scoto.fodamy.ui.profile

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.12.2021 at 15:05
 */
sealed class UIProfileEvent {
    data class ShowMessage(val message: String) : UIProfileEvent()
    data class NavigateTo(val actionId: Int) : UIProfileEvent()
}
