package com.scoto.fodamy.ui.auth.reset_password

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
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    val email = MutableLiveData("")

    val progressBarVisibility = MutableLiveData<Boolean>()

    val requiredFieldWarnings = MutableLiveData<InputErrorType<String>>()


    private var _state = SingleLiveEvent<Boolean>()
    val state: SingleLiveEvent<Boolean>
        get() = _state

    fun doForgotRequest() {
        viewModelScope.launch {
            val email = email.value.toString()

            if (validateInputs(email)) {
                setProgressbarVisibility(true)
                val response = authRepository.forgot(email)
                when (response) {
                    is NetworkResult.Success -> {
                        _state.value = true
                        setProgressbarVisibility(false)
                    }
                    is NetworkResult.Error -> {
                        requiredFieldWarnings.value =
                            InputErrorType.InvalidInputs(response.message!!.parseResponse())
                        _state.value = false
                        setProgressbarVisibility(false)
                    }
                }
            }

        }
    }

    private fun validateInputs(email: String): Boolean {
        return if (email.isNullOrBlank()) {
            requiredFieldWarnings.value = InputErrorType.Email(true)
            false
        } else {
            true
        }
    }


    private fun setProgressbarVisibility(state: Boolean) {
        progressBarVisibility.value = state
    }
}
