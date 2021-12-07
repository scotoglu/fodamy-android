package com.scoto.fodamy.ui.home

import androidx.lifecycle.LiveData
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
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    private val _event: SingleLiveEvent<UIHomeEvent> = SingleLiveEvent()
    val event: LiveData<UIHomeEvent> get() = _event


    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            //do logout request
            when (val response = authRepository.logout()) {
                is NetworkResponse.Success -> {
                    _event.value = UIHomeEvent.ShowMessage.Success(response.data.message)
                }
                is NetworkResponse.Error -> {
                    _event.value =
                        UIHomeEvent.ShowMessage.Error(response.exception.handleException())
                }
            }
        } else {
            //Navigate To login
            _event.value =
                UIHomeEvent.NavigateTo(HomeFragmentDirections.actionHomeFragmentToLoginFlow())
        }

    }

    suspend fun isLoginLiveData(): LiveData<String> =
        dataStoreManager.isLoginLiveData()


    companion object {
        private const val TAG = "HomeViewModel"
    }
}
