package com.scoto.fodamy.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.ui.home.HomeFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    private var _token: MutableLiveData<String> = MutableLiveData()
    val token: LiveData<String> get() = dataStoreManager.token.asLiveData()

    init {
        //  checkUserAuth()
        Log.d(TAG, "init: token ${token.value.toString()} ")
    }

    private var _event: SingleLiveEvent<UIMainEvent> = SingleLiveEvent()
    val event: SingleLiveEvent<UIMainEvent> get() = _event


    fun logout() = viewModelScope.launch {
        if (token.value.toString().isNotBlank()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Success -> {
//                    _event.value = UIMainEvent.NavigateTo(null, "Çıkış yapıldı.")
                    _event.value = UIMainEvent.ShowMessage(response.data.message)
                }
                is NetworkResponse.Error -> {
                    _event.value = UIMainEvent.ShowMessage(response.exception.handleException())
                }
            }
        } else {
            _event.value = UIMainEvent.NavigateTo(
                HomeFragmentDirections.actionHomeFragmentToLoginFragment2()
            )
        }
    }


    private fun checkUserAuth() = viewModelScope.launch {
        _token.value = dataStoreManager.getToken()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}
