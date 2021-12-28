package com.scoto.fodamy.ui.auth.reset_password

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
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val email = MutableLiveData("")
    val requiredFieldWarning: SingleLiveEvent<InputErrorType> = SingleLiveEvent()
    val isRequiredFieldVisible = MutableLiveData<Boolean>(false)

    fun resetPassword() =
        viewModelScope.launch {
            val email = email.value.toString()
            if (validation.value == true) {
                when (val response = authRepository.forgot(email)) {
                    is NetworkResponse.Success -> {
                        // We don't know what is the response of request.Service is not working.
                        showMessage("Email kontrol edin.")
                    }
                    is NetworkResponse.Error -> {
                        showMessage(response.exception.handleException())
                    }
                }
            }
        }

    val validation: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun validateEmail(): Boolean {
            return if (email.value?.isBlank() == true) {
                requiredFieldWarning.value = InputErrorType.Email
                isRequiredFieldVisible.value = true
                false
            } else {
                isRequiredFieldVisible.value = false
                true
            }
        }
        addSource(email) { value = validateEmail() }
    }

}
