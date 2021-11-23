package com.scoto.fodamy.network.models

data class Comment(
    val id: Int,
    val text: String,
    val difference: String,
    val user: User
)
