package com.scoto.fodamy.ui.auth.signup

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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val username = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")

    val requiredFieldWarning: SingleLiveEvent<InputErrorType> = SingleLiveEvent()

    val isRequiredFieldVisible = MutableLiveData<Boolean>()

    val validation: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(username) { value = validateUsername() }
        addSource(email) { value = validateEmail() }
        addSource(password) { value = validatePassword() }
    }

    fun register() =
        viewModelScope.launch {
            val username = username.value.toString()
            val email = email.value.toString()
            val password = password.value.toString()

            if (validation.value == true) {
                sendRequest(
                    loading = true,
                    success = {
                        authRepository.register(username, email, password)
                        showMessageWithRes(R.string.success_register)
                        resetInputFields()
                        toLogin()
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

    private fun validateEmail(): Boolean {
        return if (email.value?.isBlank() == true) {
            requiredFieldWarning.value = InputErrorType.Email
            false
        } else {
            isRequiredFieldVisible.value = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        return if (password.value?.isBlank() == true && password.value?.length!! < 6) {
            requiredFieldWarning.value = InputErrorType.Password
            false
        } else {
            isRequiredFieldVisible.value = false
            true
        }
    }

    fun toLogin() {
        navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun resetInputFields() {
        username.value = ""
        email.value = ""
        password.value = ""
    }
}
