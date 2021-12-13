package com.scoto.fodamy.helper.states

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val exception: Exception) : NetworkResponse<Nothing>()
}

internal val NetworkResponse<*>.succeeded
    get() = this is NetworkResponse.Success && data != null

fun <R, T> NetworkResponse<T>.fold(onSuccess: (T) -> R, onFailure: (Exception) -> R): R {
    return if (succeeded) {
        onSuccess((this as NetworkResponse.Success).data)
    } else {
        onFailure((this as NetworkResponse.Error).exception)
    }
}