package com.scoto.fodamy.ui.dialog.comment

import androidx.lifecycle.SavedStateHandle
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.dialog.base.BaseDialogViewModel
import com.scoto.fodamy.util.KEY_COMMENT_DELETE
import com.scoto.fodamy.util.KEY_COMMENT_EDIT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 01:13
 */
@HiltViewModel
class CommentDialogViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseDialogViewModel() {

    private val commentId = savedStateHandle.get<Int>(COMMENT_ID) ?: 1
    private val recipeId = savedStateHandle.get<Int>(RECIPE_ID) ?: 1

    fun edit() {
        setExtras(KEY_COMMENT_EDIT, true)
        close()
    }
    fun delete() {
        sendRequest(
            request = {
                recipeRepository.deleteComment(
                    recipeId = recipeId,
                    commentId = commentId
                )
            },
            success = {
                showMessageWithRes(R.string.success_comment_delete)
                setExtras(KEY_COMMENT_DELETE, true)
                close()
            }
        )
    }

    companion object {
        private const val COMMENT_ID = "commentId"
        private const val RECIPE_ID = "recipeId"
    }
}
