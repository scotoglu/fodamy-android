package com.scoto.fodamy.network.models.responses

import com.scoto.fodamy.network.models.Pagination

data class BaseResponse<T>(
    val data: T,
    val code: String,
    val message: String,
    val error: String,
    val pagination: Pagination
)
