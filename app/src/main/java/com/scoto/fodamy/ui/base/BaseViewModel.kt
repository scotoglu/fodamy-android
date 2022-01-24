package com.scoto.fodamy.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.google.gson.Gson
import com.scoto.domain.models.ErrorControl
import com.scoto.fodamy.R
import com.scoto.fodamy.helper.SingleLiveEvent
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.12.2021 at 12:43
 */
abstract class BaseViewModel : ViewModel() {

    val baseEvent: SingleLiveEvent<BaseViewEvent> = SingleLiveEvent()

    fun navigate(directions: NavDirections) {
        baseEvent.value = BaseViewEvent.NavigateTo(directions)
    }

    open fun backTo() {
        baseEvent.value = BaseViewEvent.BackTo
    }

    fun showMessage(message: String?) {
        if (message.isNullOrBlank()) return
        baseEvent.value = BaseViewEvent.ShowMessage(message)
    }

    fun openDialog(actionId: Int) {
        baseEvent.value = BaseViewEvent.OpenDialog(actionId)
    }

    fun showMessageWithRes(@StringRes messageId: Int) {
        baseEvent.value = BaseViewEvent.ShowMessageRes(messageId)
    }

    fun <T> sendRequest(
        success: suspend () -> T,
        error: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                success.invoke()
            } catch (ex: Exception) {
                if (error == null)
                    parseException(ex)
                else
                    error.invoke()
            }
        }
    }

    private fun parseException(ex: Exception) {
        when (ex) {
            is HttpException -> {
                val message = Gson().fromJson(
                    ex.response()?.errorBody()?.charStream(),
                    ErrorControl::class.java
                )
                showMessage(message.error)
            }
            is IOException -> {
                showMessageWithRes(R.string.check_internet_connection)
            }
            else -> {
                showMessage(ex.message.toString())
            }
        }
    }
}
