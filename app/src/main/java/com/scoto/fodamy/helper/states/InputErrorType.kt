package com.scoto.fodamy.helper.states

sealed class InputErrorType {
    object Email : InputErrorType()
    object Password : InputErrorType()
    object Username : InputErrorType()
}
