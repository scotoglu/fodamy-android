package com.scoto.fodamy.helper.states

sealed class InputErrorType<T>() {
    data class Email<T>(val hasError: Boolean) : InputErrorType<T>()
    data class Password<T>(val hasError: Boolean) : InputErrorType<T>()
    data class Username<T>(val hasError: Boolean) : InputErrorType<T>()
    data class InvalidInputs<T>(val message: String) : InputErrorType<T>()
}
