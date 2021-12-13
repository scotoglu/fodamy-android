package com.scoto.fodamy.domain.usecase.login

import com.scoto.fodamy.di.modules.IODispatcher
import com.scoto.fodamy.domain.utils.UseCase
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.helper.states.succeeded
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.ui.auth.UIAuthEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val loginUseCaseMapper: LoginUseCaseMapper,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : UseCase.FlowUseCase<LoginParams, Boolean> {

    override operator fun invoke(params: LoginParams): Flow<NetworkResponse<Boolean>> =
        authRepository.login(username = params.username, password = params.password)
            .flatMapLatest { result ->
                flow {
                    if (result.succeeded) {
                        result as NetworkResponse.Success
                        val mappedResult = loginUseCaseMapper.mapTo(result.data)
                        emit(NetworkResponse.Success(mappedResult))
                        return@flow
                    }
                    result as NetworkResponse.Error
                    emit(NetworkResponse.Error(result.exception))
                }
            }.flowOn(dispatcher)


}

