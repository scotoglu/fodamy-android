package com.scoto.fodamy.ui.home

import androidx.lifecycle.viewModelScope
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val event: SingleLiveEvent<HomeViewEvent> = SingleLiveEvent()

    init {
        isLogin()
    }

    private fun isLogin() = viewModelScope.launch {
        event.value = HomeViewEvent.IsLogin(dataStoreManager.isLogin())
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                success = {
                    val res = authRepository.logout()
                    event.value = HomeViewEvent.Success
                    showMessage(res.message)
                }
            )
        }
    }
}
