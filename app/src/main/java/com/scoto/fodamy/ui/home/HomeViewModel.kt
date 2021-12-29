package com.scoto.fodamy.ui.home

import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val viewState: SingleLiveEvent<HomeViewState> = SingleLiveEvent()

    init {
        isLogin()
    }

    private fun isLogin() = viewModelScope.launch {
        viewState.value = HomeViewState.IsLogin(dataStoreManager.isLogin())
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Error -> {
                    showMessage(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    viewState.value = HomeViewState.Success(response.data.message)
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
