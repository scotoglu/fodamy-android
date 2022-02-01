package com.scoto.fodamy.ui.dialog.unfollow

import androidx.lifecycle.SavedStateHandle
import com.scoto.domain.repositories.UserRepository
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.dialog.base.BaseDialogViewModel
import com.scoto.fodamy.util.KEY_UNFOLLOW_COMPLETED
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 01:00
 */
@HiltViewModel
class UnfollowViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseDialogViewModel() {

    private val userId = savedStateHandle.get<Int>(USER_ID) ?: 0
    fun unfollow() {
        sendRequest(
            request = { userRepository.unFollowUser(userId) },
            success = {
                showMessageWithRes(R.string.success_unfollow)
                setExtras(KEY_UNFOLLOW_COMPLETED, true)
                close()
            }
        )
    }

    companion object {
        private const val USER_ID = "userId"
    }
}
