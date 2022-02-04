package com.scoto.fodamy.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.scoto.data.network.exceptions.Authentication
import com.scoto.data.network.exceptions.BaseException
import com.scoto.data.network.exceptions.GettingEmptyListItem
import com.scoto.data.network.exceptions.SimpleHttpException
import com.scoto.fodamy.R
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.util.ResultListenerParams
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * @author Sefa ÇOTOĞLU
 * Created 28.12.2021 at 12:43
 */
abstract class BaseViewModel : ViewModel(), FetchExtras {

    val baseEvent: SingleLiveEvent<BaseViewEvent> = SingleLiveEvent()

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    @CallSuper
    override fun fetchExtras(bundle: Bundle?) {

    }

    fun navigate(directions: NavDirections) {
        baseEvent.value = BaseViewEvent.NavigateTo(directions)
    }

    fun setExtras(params:ResultListenerParams) {
        baseEvent.value = BaseViewEvent.Extras(params)
    }

    open fun backTo() {
        baseEvent.value = BaseViewEvent.BackTo
    }

    fun showMessage(message: String?) {
        if (message.isNullOrBlank()) return
        baseEvent.value = BaseViewEvent.ShowMessage(message)
    }

    fun openNavigationDilog(actionId: Int) {
        baseEvent.value = BaseViewEvent.OpenDialog(actionId)
    }

    fun showMessageWithRes(@StringRes messageId: Int) {
        baseEvent.value = BaseViewEvent.ShowMessageRes(messageId)
    }

    private fun showDialog() {
        _loading.value = true
    }

    private fun hideDialog() {
        _loading.value = false
    }

    fun <T> sendRequest(
        loading: Boolean = false,
        request: suspend () -> T,
        success: (suspend (T) -> Unit)? = null,
        error: ((Exception) -> Unit)? = null
    ): Job {
        return viewModelScope.launch {
            if (loading) showDialog()
            try {
                val response = request.invoke()
                success?.invoke(response)
                hideDialog()
            } catch (ex: Exception) {
                hideDialog()
                if (error == null)
                    handleException(ex)
                else error.invoke(ex)
            }
        }
    }

    private fun handleException(ex: Exception) {
        when (ex) {
            is Authentication -> showMessageWithRes(R.string.try_to_login)
            is IOException -> showMessageWithRes(R.string.check_internet_connection)
            is GettingEmptyListItem -> showMessageWithRes(R.string.no_comment_in_list)
            is SimpleHttpException -> showMessage(ex.friendlyMessage)
            is BaseException -> showMessage(ex.exMessage)
        }
    }
}
