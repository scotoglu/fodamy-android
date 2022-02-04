package com.scoto.fodamy.ui.dialog.unfollow

import android.os.Bundle
import com.scoto.domain.repositories.UserRepository
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.util.DIALOG_ACTION
import com.scoto.fodamy.util.KEY_UNFOLLOW_COMPLETED
import com.scoto.fodamy.util.ResultListenerParams
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 01:00
 */
@HiltViewModel
class UnfollowViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var userId: Int? = null

    override fun fetchExtras(bundle: Bundle?) {
        super.fetchExtras(bundle)
        userId = bundle?.getInt(USER_ID) ?: -1
    }

    fun unfollow() {
        sendRequest(
            loading = true,
            request = { userRepository.unFollowUser(userId!!) },
            success = {
                showMessageWithRes(R.string.success_unfollow)
                setExtras(ResultListenerParams(DIALOG_ACTION, KEY_UNFOLLOW_COMPLETED, true))
                backTo()
            }
        )
    }

    companion object {
        private const val USER_ID = "userId"
    }
}
