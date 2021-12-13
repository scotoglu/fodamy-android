package com.scoto.fodamy.ui.auth.login

import androidx.lifecycle.*
import com.scoto.fodamy.domain.usecase.login.LoginParams
import com.scoto.fodamy.domain.usecase.login.LoginUseCase
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.ui.auth.UIAuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val loginJob = Job()

    val username = MutableLiveData("scoto")
    val password = MutableLiveData("123456**")
    val progressbarVisibility = MutableLiveData<Boolean>()

    val checkValidateInputs: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        fun validateUsername(): Boolean {
            return if (username.value?.isBlank() == true) {
                _requiredFieldWarning.value = InputErrorType.Email(true)
                false
            } else
                true
        }

        fun validatePassword(): Boolean {
            return if (password.value?.isBlank() == true) {
                _requiredFieldWarning.value = InputErrorType.Password(true)
                false
            } else
                true
        }

        addSource(username) { value = validateUsername() }
        addSource(password) { value = validatePassword() }

    }

    private var _event = MutableLiveData<LoginViewEvent>()
    val state: LiveData<UIAuthEvent>
        get() = _event.switchMap {
            when (it) {
                LoginViewEvent.DoLogin -> {
                    login()
                }
                else -> {
                    login()
                }
            }
        }


    private val _requiredFieldWarning: MutableLiveData<InputErrorType> = MutableLiveData()
    val requiredFieldWarning: LiveData<InputErrorType>
        get() = _requiredFieldWarning

    fun login(): LiveData<UIAuthEvent> {
        val username = username.value.toString()
        val password = password.value.toString()
        val params = LoginParams(username, password)

        return loginUseCase.invoke(params).map {
            when (it) {
                is NetworkResponse.Error -> UIAuthEvent.NavigateTo(
                    LoginFragmentDirections.actionLoginFragmentToRegisterFragment(),
                    ""
                )
                is NetworkResponse.Success -> UIAuthEvent.BackTo("Giris Basarili")
            }

        }.asLiveData(loginJob).distinctUntilChanged()
    }


    fun doLoginRequest() = viewModelScope.launch {
        if (checkValidateInputs.value == true) {
            _event.value = LoginViewEvent.DoLogin
        }
    }


    fun registerOnClick() {
        _event.value = LoginViewEvent.NavigateToRegister
    }

    fun forgotPasswordOnClick() {
        _event.value = LoginViewEvent.NavigateToForgotPassword
    }

    private fun setProgressbarVisibility(state: Boolean) {
        progressbarVisibility.value = state
    }

    override fun onCleared() {
        super.onCleared()
        loginJob.cancel()
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }

}