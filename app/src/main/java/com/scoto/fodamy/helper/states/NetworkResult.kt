package com.scoto.fodamy.helper.states

import okhttp3.ResponseBody

sealed class NetworkResult<T>(
) {
   data class Success<T>(val data: T) : NetworkResult<T>()
   data class Error<T>(val message: ResponseBody?) : NetworkResult<T>()
    //{
//       data class IOError<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
//       data class HttpError<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    //  }
}