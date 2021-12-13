package com.scoto.fodamy.domain.utils

import com.scoto.fodamy.helper.states.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface UseCase {
    @FunctionalInterface
    interface FlowUseCase<in P, out D> : UseCase where P : Params {
        fun invoke(params: P) : Flow<NetworkResponse<D>>
    }
}
