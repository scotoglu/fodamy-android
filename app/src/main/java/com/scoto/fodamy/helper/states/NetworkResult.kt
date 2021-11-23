package com.scoto.fodamy.helper.states

import okhttp3.ResponseBody

sealed class NetworkResult<T>(
) {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val message: ResponseBody?) : NetworkResult<T>() {
        data class IOError<T>(val message: String?) : NetworkResult<T>()
        data class HttpError<T>(val message: String?) : NetworkResult<T>()
        data class OutOfIndexError<T>(val message: String) : NetworkResult<T>()
    }
}