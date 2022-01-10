package com.scoto.fodamy.ui.auth.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val username = MutableLiveData("scoto")
    val password = MutableLiveData("123456**")
    val requiredFieldWarning: SingleLiveEvent<InputErrorType> = SingleLiveEvent()
    val isRequiredFieldVisible = MutableLiveData<Boolean>()

    fun login() =
        viewModelScope.launch {
            val username = username.value.toString()
            val password = password.value.toString()

            if (validation.value == true) {
                when (val response = authRepository.login(username, password)) {
                    is NetworkResponse.Success -> {
                        // Api response is consist of token and user, doesn't contain any messages.
                        showMessage("Giriş Başarılı")
                        backTo()
                    }
                    is NetworkResponse.Error -> {
                        showMessage(response.exception.handleException())
                    }
                }
            }
        }

    val validation: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun validateUsername(): Boolean {
            return if (username.value?.isBlank() == true) {
                requiredFieldWarning.value = InputErrorType.Username
                false
            } else {
                isRequiredFieldVisible.value = false
                true
            }
        }

        fun validatePassword(): Boolean {
            return if (password.value?.isBlank() == true) {
                requiredFieldWarning.value = InputErrorType.Password
                false
            } else {
                isRequiredFieldVisible.value = false
                true
            }
        }

        addSource(username) { value = validateUsername() }
        addSource(password) { value = validatePassword() }
    }

    fun toRegister() {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    fun toForgotPassword() {
        navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}
