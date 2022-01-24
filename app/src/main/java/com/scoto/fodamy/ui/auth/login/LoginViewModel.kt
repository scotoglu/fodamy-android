package com.scoto.fodamy.ui.auth.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.domain.repositories.AuthRepository
import com.scoto.fodamy.R
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.InputErrorType
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

    val validation: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(username) { value = validateUsername() }
        addSource(password) { value = validatePassword() }
    }

    fun login() =
        viewModelScope.launch {
            val username = username.value.toString()
            val password = password.value.toString()

            if (validation.value == true) {
                sendRequest(
                    loading = true,
                    success = {
                        authRepository.login(username, password)
                        showMessageWithRes(R.string.success_login)
                        backTo()
                    }
                )
            }
        }

    private fun validateUsername(): Boolean {
        return if (username.value?.isBlank() == true) {
            requiredFieldWarning.value = InputErrorType.Username
            false
        } else {
            isRequiredFieldVisible.value = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (password.value?.isBlank() == true) {
            requiredFieldWarning.value = InputErrorType.Password
            false
        } else {
            isRequiredFieldVisible.value = false
            true
        }
    }

    fun toRegister() {
        navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    fun toForgotPassword() {
        navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
    }
}
