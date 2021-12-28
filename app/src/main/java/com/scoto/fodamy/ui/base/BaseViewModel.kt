package com.scoto.fodamy.ui.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.scoto.fodamy.helper.SingleLiveEvent

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.12.2021 at 12:43
 */
abstract class BaseViewModel : ViewModel() {

    val baseEvent: SingleLiveEvent<BaseViewState> = SingleLiveEvent()

    fun navigate(directions: NavDirections) {
        baseEvent.value = BaseViewState.NavigateTo(directions)
    }

    fun backTo() {
        baseEvent.value = BaseViewState.BackTo
    }

    fun showMessage(message: String?) {
        if (message.isNullOrBlank()) return
        baseEvent.value = BaseViewState.ShowMessage(message)
    }

    fun openDialog(actionId: Int) {
        baseEvent.value = BaseViewState.OpenDialog(actionId)
    }
}