package com.scoto.fodamy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    val event: SingleLiveEvent<UIHomeEvent> = SingleLiveEvent()


    fun isLogin() = viewModelScope.launch {
        event.value = UIHomeEvent.IsLogin(dataStoreManager.isLogin())
    }

    fun onEndClick() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Error -> {
                    event.value =
                        UIHomeEvent.ShowMessage.Error(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    event.value = UIHomeEvent.ShowMessage.Success(response.data.message)
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
