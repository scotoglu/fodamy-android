package com.scoto.data.remote.exceptions

/**
 * @author Sefa ÇOTOĞLU
 * Created 26.01.2022 at 20:59
 */
data class SimpleHttpException(
    val code: String?,
    val friendlyMessage: String?
) : Exception()
