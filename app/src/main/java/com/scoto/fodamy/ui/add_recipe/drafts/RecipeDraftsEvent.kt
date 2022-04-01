package com.scoto.fodamy.ui.add_recipe.drafts

/**
 * @author Sefa ÇOTOĞLU
 * Created 24.03.2022
 */
sealed class RecipeDraftsEvent {
    data class IsLogin(val isLogin: Boolean) : RecipeDraftsEvent()
    object Success : RecipeDraftsEvent()
}
