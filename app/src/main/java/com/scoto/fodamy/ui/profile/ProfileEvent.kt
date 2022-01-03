package com.scoto.fodamy.ui.profile

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.12.2021 at 15:05
 */
sealed class ProfileEvent {

    data class Success(val message: String) : ProfileEvent()
    data class IsLogin(val isLogin: Boolean) : ProfileEvent()
}
