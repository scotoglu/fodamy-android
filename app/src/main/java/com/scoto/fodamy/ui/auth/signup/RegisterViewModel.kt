package com.scoto.fodamy.ui.auth.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.ext.parseResponse
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.helper.states.NetworkResult
import com.scoto.fodamy.network.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val username = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val progressbarVisibility = MutableLiveData<Boolean>()


    private var _requiredFieldWarnings = MutableLiveData<InputErrorType<Boolean>>()
    val requiredFieldWarning: MutableLiveData<InputErrorType<Boolean>>
        get() = _requiredFieldWarnings


    private var _state = SingleLiveEvent<Boolean>()
    val state: SingleLiveEvent<Boolean>
        get() = _state


    fun doRegisterRequest() {
        viewModelScope.launch {

            val username = username.value.toString()
            val email = email.value.toString()
            val password = password.value.toString()


            if (validateInputs(username, email, password)) {
                setProgreessbarVisibility(true)
                val response = authRepository.register(username, email, password)
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = true
                        setProgreessbarVisibility(false)
                    }
                    is NetworkResult.Error -> {
                        requiredFieldWarning.value =
                            InputErrorType.InvalidInputs(response.message!!.parseResponse())
                        _state.value = false
                        setProgreessbarVisibility(false)
                    }
                }
            }
        }
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean {
        when {
            username.isNullOrBlank() -> {
                requiredFieldWarning.value = InputErrorType.Username(true)
            }
            email.isNullOrBlank() -> {
                requiredFieldWarning.value = InputErrorType.Email(true)
            }
            password.isNullOrBlank() -> {
                requiredFieldWarning.value = InputErrorType.Password(true)
            }
            else -> {
                return true
            }
        }
        return false
    }

    private fun setProgreessbarVisibility(state: Boolean) {
        progressbarVisibility.value = state

    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}
