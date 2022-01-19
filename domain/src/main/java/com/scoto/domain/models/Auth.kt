package com.scoto.domain.models

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 15:43
 */
data class Auth(
    val token: String,
    val user: User
)
