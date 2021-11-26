package com.scoto.fodamy.helper.states

sealed class NetworkResponse<T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Error<T>(val exception: Exception) : NetworkResponse<T>()
}
