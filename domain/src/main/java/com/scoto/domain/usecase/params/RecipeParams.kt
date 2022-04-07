package com.scoto.domain.usecase.params

import com.scoto.domain.usecase.base.Params

/**
 * @author Sefa ÇOTOĞLU
 * Created 31.01.2022 at 16:39
 */
data class RecipeParams(val recipeId: Int, val onlyRemote: Boolean) : Params()
