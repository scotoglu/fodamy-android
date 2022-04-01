package com.scoto.data.local.local_dto

/**
 * @author Sefa ÇOTOĞLU
 * Created 16.03.2022
 */
data class CategoryDraftDb(
    val id: Int,
    val name: String,
    val mainCategoryId: Int?
)
