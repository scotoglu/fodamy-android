package com.scoto.fodamy.helper.states

sealed class InputErrorType {
    data class Email(val hasError: Boolean) : InputErrorType()
    data class Password(val hasError: Boolean) : InputErrorType()
    data class Username(val hasError: Boolean) : InputErrorType()
    data class ShowMessage(val message: String) : InputErrorType()
    object CloseMessage : InputErrorType()
}
