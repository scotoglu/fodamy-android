package com.scoto.domain.models

/**
 * @author Sefa ÇOTOĞLU
 * Created 19.01.2022 at 19:34
 */
data class Pagination(
    val currentPage: Int,
    val firstItem: Int,
    val lastItem: Int,
    val lastPage: Int,
    val perPage: Int,
    val total: Int
)
