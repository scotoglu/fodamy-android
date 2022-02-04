package com.scoto.fodamy.ui.base

import androidx.annotation.StringRes
import androidx.navigation.NavDirections
import com.scoto.fodamy.util.ResultListenerParams

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.12.2021 at 12:43
 */
sealed class BaseViewEvent {
    data class NavigateTo(val directions: NavDirections) : BaseViewEvent()
    data class ShowMessage(val message: String) : BaseViewEvent()
    data class OpenDialog(val actionId: Int) : BaseViewEvent()
    data class ShowMessageRes(@StringRes val messageId: Int) : BaseViewEvent()
    data class Extras(val params: ResultListenerParams) : BaseViewEvent()
    object BackTo : BaseViewEvent()
}
