package com.scoto.fodamy.helper.states

import okhttp3.ResponseBody

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: ResponseBody? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: ResponseBody?, data: T? = null) : NetworkResult<T>(data, message)
    //{
//        class IOError<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
//        class HttpError<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    //  }
}