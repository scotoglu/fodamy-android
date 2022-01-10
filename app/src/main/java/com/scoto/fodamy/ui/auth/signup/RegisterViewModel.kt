package com.scoto.fodamy.ui.auth.signup

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.R
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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val username = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val progressbarVisibility = MutableLiveData<Boolean>()

    val requiredFieldWarning: SingleLiveEvent<InputErrorType> = SingleLiveEvent()
    val isRequiredFieldVisible = MutableLiveData<Boolean>()

    fun register() =
        viewModelScope.launch {
            val username = username.value.toString()
            val email = email.value.toString()
            val password = password.value.toString()

            if (validation.value == true) {
                when (val response = authRepository.register(username, email, password)) {
                    is NetworkResponse.Success -> {
                        // Api response is consist of token and user, doesn't contain any messages.
                        showMessageWithRes(R.string.success_register)
                        toLogin()
                        resetInputFields()
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

        fun validateEmail(): Boolean {
            return if (email.value?.isBlank() == true) {
                requiredFieldWarning.value = InputErrorType.Email
                false
            } else {
                isRequiredFieldVisible.value = false
                true
            }
        }

        fun validatePassword(): Boolean {
            return if (password.value?.isBlank() == true && password.value?.length!! < 6) {
                requiredFieldWarning.value = InputErrorType.Password
                false
            } else {
                isRequiredFieldVisible.value = false
                true
            }
        }
        addSource(username) { value = validateUsername() }
        addSource(email) { value = validateEmail() }
        addSource(password) { value = validatePassword() }
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
