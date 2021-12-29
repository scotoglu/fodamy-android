package com.scoto.fodamy.ui.base

import androidx.navigation.NavDirections

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.12.2021 at 12:43
 */
sealed class BaseViewState {
    data class NavigateTo(val directions: NavDirections) : BaseViewState()
    data class ShowMessage(val message: String) : BaseViewState()
    data class OpenDialog(val actionId: Int) : BaseViewState()
    object BackTo : BaseViewState()
}