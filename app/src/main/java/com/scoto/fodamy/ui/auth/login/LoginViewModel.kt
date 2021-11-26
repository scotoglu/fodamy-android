package com.scoto.fodamy.ui.auth.login

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    val username = MutableLiveData("scoto")
    val password = MutableLiveData("123456**")
    val progressbarVisibility = MutableLiveData<Boolean>()


    private var _state = SingleLiveEvent<UIAuthEvent>()
    val state: SingleLiveEvent<UIAuthEvent>
        get() = _state


    private val _requiredFieldWarning: MutableLiveData<InputErrorType> = MutableLiveData()
    val requiredFieldWarning: LiveData<InputErrorType>
        get() = _requiredFieldWarning


    fun doLoginRequest() =
        viewModelScope.launch {
            val username = username.value.toString()
            val password = password.value.toString()

            if (validateInputs(username, password)) {
                setProgressbarVisibility(true)
                when (val response = authRepository.login(username, password)) {
                    is NetworkResponse.Success -> {
                        _state.value =
                            UIAuthEvent.NavigateTo(
                                LoginFragmentDirections
                                    .actionLoginFragment2ToHomeFragment(), "Giriş başarılı."
                            )
                        _requiredFieldWarning.value = InputErrorType.CloseMessage
                        setProgressbarVisibility(false)
                    }
                    is NetworkResponse.Error -> {
                        _requiredFieldWarning.value =
                            InputErrorType.ShowMessage(response.exception.handleException())
                        setProgressbarVisibility(false)
                    }
                }
            }
        }


    fun registerOnClick() {
        _state.value =
            UIAuthEvent.NavigateTo(
                LoginFragmentDirections.actionLoginFragment2ToRegisterFragment2()
            )
    }

    fun forgotPasswordOnClick() {
        _state.value =
            UIAuthEvent.NavigateTo(
                LoginFragmentDirections.actionLoginFragment2ToResetPasswordFragment2()
            )
    }

    private fun validateInputs(username: String, password: String): Boolean {
        var isValid = false
        if (username.isBlank()) {
            _requiredFieldWarning.value = InputErrorType.Email(true)
        } else {
            if (password.isBlank()) {
                _requiredFieldWarning.value = InputErrorType.Password(true)
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