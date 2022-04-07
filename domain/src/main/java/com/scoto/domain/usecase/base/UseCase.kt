package com.scoto.domain.usecase.base

/**
 * @author Sefa ÇOTOĞLU
 * Created 31.01.2022 at 16:33
 */

abstract class UseCase<P, T> where P : Params {
    abstract suspend fun invoke(params: P): T
}
