package com.scoto.data.utils

import com.scoto.data.di.ApplicationScope
import com.scoto.domain.utils.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Sefa ÇOTOĞLU
 * Created 20.01.2022 at 10:14
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @ApplicationScope private val scope: CoroutineScope
) : Interceptor {

    private var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        getToken()
        val request = chain.request().newBuilder()
        if (token.isNullOrBlank()) return chain.proceed(request.build())
        request.addHeader("X-Fodamy-Token", token.toString())
        return chain.proceed(request = request.build())
    }

    private fun getToken() {
        scope.launch {
            token = dataStoreManager.getToken()
        }
    }
}