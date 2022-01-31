package com.scoto.domain.usecase

import com.scoto.domain.models.Common
import com.scoto.domain.repositories.AuthRepository
import com.scoto.domain.usecase.base.UseCase
import com.scoto.domain.usecase.params.NoParams
import javax.inject.Inject


/**
 * @author Sefa ÇOTOĞLU
 * Created 31.01.2022 at 11:49
 */
class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) : UseCase<NoParams, Common>() {
    override suspend fun invoke(params: NoParams): Common {
        return authRepository.logout()
    }
}