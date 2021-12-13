package com.scoto.fodamy.domain.usecase.login

import com.scoto.fodamy.domain.utils.Params

data class LoginParams(val username: String, val password: String) : Params()