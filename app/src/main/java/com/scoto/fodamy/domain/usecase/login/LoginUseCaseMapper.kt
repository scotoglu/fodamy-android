package com.scoto.fodamy.domain.usecase.login

import com.scoto.fodamy.di.modules.IODispatcher
import com.scoto.fodamy.domain.utils.Mapper
import com.scoto.fodamy.network.models.responses.AuthResponse
import com.scoto.fodamy.ui.auth.UIAuthEvent
import com.scoto.fodamy.ui.auth.login.LoginFragmentDirections
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUseCaseMapper @Inject constructor(
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : Mapper<AuthResponse, Boolean> {

    override suspend fun mapTo(item: AuthResponse): Boolean = withContext(dispatcher) {
        return@withContext item.token.isNotBlank() && item.token.isNotEmpty()
    }

}