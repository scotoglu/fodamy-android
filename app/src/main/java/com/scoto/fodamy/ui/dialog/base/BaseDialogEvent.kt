package com.scoto.fodamy.ui.dialog.base

import androidx.annotation.StringRes

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 01:04
 */
sealed class BaseDialogEvent {
    data class ShowMessage(val message: String) : BaseDialogEvent()
    data class ShowMessageWithRes(@StringRes val message: Int) : BaseDialogEvent()
    data class Extras(val key: String, val value: Any) : BaseDialogEvent()
    object Close : BaseDialogEvent()
}
