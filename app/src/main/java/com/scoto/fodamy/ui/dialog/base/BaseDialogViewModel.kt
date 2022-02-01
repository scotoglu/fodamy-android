package com.scoto.fodamy.ui.dialog.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.data.network.exceptions.Authentication
import com.scoto.data.network.exceptions.BaseException
import com.scoto.data.network.exceptions.GettingEmptyListItem
import com.scoto.data.network.exceptions.SimpleHttpException
import com.scoto.fodamy.R
import com.scoto.fodamy.helper.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 01:01
 */
abstract class BaseDialogViewModel : ViewModel() {

    val event = SingleLiveEvent<BaseDialogEvent>()

    fun <T> sendRequest(
        loading: Boolean = false,
        request: suspend () -> T,
        success: (suspend (T) -> Unit)? = null,
        error: ((Exception) -> Unit)? = null
    ): Job {
        return viewModelScope.launch {
            try {
                val response = request.invoke()
                success?.invoke(response)
            } catch (ex: Exception) {
                if (error == null)
                    handleException(ex)
                else error.invoke(ex)
            }
        }
    }

    fun setExtras(key:String,value:Any){
        event.value  = BaseDialogEvent.Extras(key,value)
    }

    fun showMessageWithRes(@StringRes message: Int) {
        event.value = BaseDialogEvent.ShowMessageWithRes(message)
    }

    fun showMessage(message: String?) {
        if (message == null) {
            return
        }
        event.value = BaseDialogEvent.ShowMessage(message)
    }
    fun close(){
        event.value = BaseDialogEvent.Close
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

