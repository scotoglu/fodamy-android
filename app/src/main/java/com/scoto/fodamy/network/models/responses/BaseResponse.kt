package com.scoto.fodamy.network.models.responses

data class BaseResponse(
    val code: String,
    val message: String,
    val error: String
)
