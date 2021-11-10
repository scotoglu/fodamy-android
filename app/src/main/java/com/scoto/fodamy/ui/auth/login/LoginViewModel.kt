package com.scoto.fodamy.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.ext.parseResponse
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val username = MutableLiveData("")
    val password = MutableLiveData("")


    private var _state = SingleLiveEvent<Boolean>()
    val state: SingleLiveEvent<Boolean>
        get() = _state


    val requiredFieldWarning = MutableLiveData<InputErrorType<Boolean>>()

    val progressbarVisibility = MutableLiveData<Boolean>()


    fun doLoginRequest() {
        viewModelScope.launch {
            val username = username.value.toString()
            val password = password.value.toString()

            if (validateInputs(username, password)) {
                setProgressbarVisibility(true)
                val response = authRepository.login(username, password)
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = true
                        setProgressbarVisibility(false)
                    }
                    is NetworkResult.Error -> {
                        requiredFieldWarning.value =
                            InputErrorType.InvalidInputs(response.message!!.parseResponse())
                        _state.value = false
                        setProgressbarVisibility(false)
                    }
                }
            }
        }
    }


    private fun validateInputs(username: String, password: String): Boolean {
        var isValid = false
        if (username.isNullOrBlank()) {
            requiredFieldWarning.value = InputErrorType.Email(true)
        } else {
            if (password.isNullOrBlank()) {
                requiredFieldWarning.value = InputErrorType.Password(true)
            } else {
                isValid = true
            }
        }
        return isValid
    }

    private fun setProgressbarVisibility(state: Boolean) {
        progressbarVisibility.value = state
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}

data class ErrorResponse(val code: String, val error: String)