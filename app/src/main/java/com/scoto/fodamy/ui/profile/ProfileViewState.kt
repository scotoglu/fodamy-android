package com.scoto.fodamy.ui.profile

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.12.2021 at 15:05
 */
sealed class ProfileViewState {

    data class Success(val message: String) : ProfileViewState()
    data class IsLogin(val isLogin: Boolean) : ProfileViewState()
}
