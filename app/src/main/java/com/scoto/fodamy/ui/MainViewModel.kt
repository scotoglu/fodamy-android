package com.scoto.fodamy.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.ext.parseResponse
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private var _token: MutableLiveData<String> = MutableLiveData("")

    val navigateTo: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private var _state: MutableLiveData<String> = MutableLiveData()
    val state: LiveData<String> get() = _state

    fun logout() = viewModelScope.launch {
        checkUserAuth()
        if (_token.value.toString().isNotBlank()) {
            //user login before
            when (val response = authRepository.logout()) {
                is NetworkResult.Success -> {
                    _state.value = response.data.toString()
                }
                is NetworkResult.Error -> {
                    _state.value = response.message?.parseResponse()
                }
            }
        } else {
            navigateTo.value = true
        }
    }


    private suspend fun checkUserAuth() {
        _token.value = dataStoreManager.getToken()
    }
}
