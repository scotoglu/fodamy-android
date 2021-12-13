package com.scoto.fodamy.network.coroutines

import com.scoto.fodamy.helper.states.NetworkResponse
import kotlinx.coroutines.flow.Flow

@FunctionalInterface
interface AsyncManager {
    fun <T> handleAsyncWithTryCatch(body: suspend () -> T): Flow<NetworkResponse<T>>
    fun <T> handleWithTryCatch(body: suspend () -> T): Flow<NetworkResponse<T>>
}
