package com.scoto.fodamy.ui.auth.login

sealed class LoginViewEvent {
    object DoLogin : LoginViewEvent()
    object NavigateToRegister : LoginViewEvent()
    object NavigateToForgotPassword : LoginViewEvent()
}