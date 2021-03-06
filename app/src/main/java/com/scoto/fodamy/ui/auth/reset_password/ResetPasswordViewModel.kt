package com.scoto.fodamy.ui.auth.reset_password

import androidx.lifecycle.MutableLiveData
import com.scoto.domain.repositories.AuthRepository
import com.scoto.fodamy.R
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    val email = MutableLiveData("")
    val requiredFieldWarning: SingleLiveEvent<InputErrorType> = SingleLiveEvent()
    val isRequiredFieldVisible = MutableLiveData<Boolean>(false)

    fun resetPassword() {
        val email = email.value.toString()
        if (validateEmail()) {
            sendRequest(
                loading = true,
                request = { authRepository.forgot(email) },
                success = {
                    showMessageWithRes(R.string.success_reset_password)
                }
            )
        }
    }

    private fun validateEmail(): Boolean {
        return if (email.value?.isBlank() == true) {
            requiredFieldWarning.value = InputErrorType.Email
            isRequiredFieldVisible.value = true
            false
        } else {
            isRequiredFieldVisible.value = false
            true
        }
    }
}
