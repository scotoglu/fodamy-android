package com.scoto.fodamy.ui.auth.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.ui.auth.UIAuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val email = MutableLiveData("")

    val progressBarVisibility = MutableLiveData<Boolean>()

    private val _requiredFieldWarnings: MutableLiveData<InputErrorType> = MutableLiveData()
    val requiredFieldWarnings: LiveData<InputErrorType> get() = _requiredFieldWarnings

    private var _state = SingleLiveEvent<UIAuthEvent>()
    val state: SingleLiveEvent<UIAuthEvent>
        get() = _state

    fun doForgotRequest() =
        viewModelScope.launch {
            val email = email.value.toString()
            if (validateInputs(email)) {
                setProgressbarVisibility(true)
                when (val response = authRepository.forgot(email)) {
                    is NetworkResponse.Success -> {
                        _state.value =
                            UIAuthEvent.NavigateTo(null, "Email adresinizi kontrol edin.")
                        setProgressbarVisibility(false)
                    }
                    is NetworkResponse.Error -> {
                        _requiredFieldWarnings.value =
                            InputErrorType.ShowMessage(response.exception.handleException())
                        setProgressbarVisibility(false)
                    }
                }
            }
        }

    private fun validateInputs(email: String): Boolean {
        return if (email.isBlank()) {
            _requiredFieldWarnings.value = InputErrorType.Email(true)
            false
        } else {
            true
        }
    }

    private fun setProgressbarVisibility(state: Boolean) {
        progressBarVisibility.value = state
    }
}
