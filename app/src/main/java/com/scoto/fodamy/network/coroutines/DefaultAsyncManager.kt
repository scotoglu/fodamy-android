package com.scoto.fodamy.network.coroutines

import com.scoto.fodamy.di.modules.IODispatcher
import com.scoto.fodamy.helper.states.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
internal class DefaultAsyncManager(
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : AsyncManager {

    override fun <T> handleAsyncWithTryCatch(body: suspend () -> T): Flow<NetworkResponse<T>> {
        return channelFlow<NetworkResponse<T>> {
            while (!isClosedForSend) {
                send(NetworkResponse.Success(body()))
                delay(DELAY_IN_MS)
            }
        }.catch { e ->
            emit(NetworkResponse.Error(Exception(e.message.toString())))
        }.flowOn(dispatcher)
    }

    override fun <T> handleWithTryCatch(body: suspend () -> T): Flow<NetworkResponse<T>> {
        return channelFlow<NetworkResponse<T>> {
            send(NetworkResponse.Success(body()))
        }.catch { e ->
            emit(NetworkResponse.Error(Exception(e.message.toString())))
        }.flowOn(dispatcher)
    }

    private companion object {
        private const val DELAY_IN_MS = 5000L
    }
}
