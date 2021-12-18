package com.scoto.fodamy.ui.auth.signup

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
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    val username = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val progressbarVisibility = MutableLiveData<Boolean>()


    private var _requiredFieldWarnings = MutableLiveData<InputErrorType>()
    val requiredFieldWarning: MutableLiveData<InputErrorType>
        get() = _requiredFieldWarnings


    val event = SingleLiveEvent<UIAuthEvent>()


    fun doRegisterRequest() =
        viewModelScope.launch {

            val username = username.value.toString()
            val email = email.value.toString()
            val password = password.value.toString()


            if (validateInputs(username, email, password)) {
                setProgressbarVisibility(true)
                when (val response = authRepository.register(username, email, password)) {
                    is NetworkResponse.Success -> {
                        event.value =
                            UIAuthEvent.NavigateTo(
                                null, "Kullanıcı kaydedildi.Giriş ekranından giriş yapabilirsiniz."
                            )
                        resetInputFields()
                        requiredFieldWarning.value = InputErrorType.CloseMessage
                        setProgressbarVisibility(false)
                    }
                    is NetworkResponse.Error -> {
                        requiredFieldWarning.value =
                            InputErrorType.ShowMessage(response.exception.handleException())
                        setProgressbarVisibility(false)
                    }
                }
            }
        }

    fun loginOnClick() {
        event.value =
            UIAuthEvent.NavigateTo(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean {
        when {
            username.isBlank() -> {
                requiredFieldWarning.value = InputErrorType.Username(true)
            }
            email.isBlank() -> {
                requiredFieldWarning.value = InputErrorType.Email(true)
            }
            password.isBlank() -> {
                requiredFieldWarning.value = InputErrorType.Password(true)
            }
            else -> {
                return true
            }
        }
        return false
    }

    private fun resetInputFields() {
        username.value = ""
        email.value = ""
        password.value = ""
    }

    private fun setProgressbarVisibility(state: Boolean) {
        progressbarVisibility.value = state

    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }

}
