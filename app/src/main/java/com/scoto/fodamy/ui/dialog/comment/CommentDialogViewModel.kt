package com.scoto.fodamy.ui.dialog.comment

import android.os.Bundle
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.util.DIALOG_ACTION
import com.scoto.fodamy.util.KEY_COMMENT_DELETE
import com.scoto.fodamy.util.KEY_COMMENT_EDIT
import com.scoto.fodamy.util.ResultListenerParams
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 1.02.2022 at 01:13
 */
@HiltViewModel
class CommentDialogViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    private var commentId: Int = -1
    private var recipeId: Int = -1

    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        commentId = CommentDialogArgs.fromBundle(bundle).commentId
        recipeId = CommentDialogArgs.fromBundle(bundle).recipeId
    }

    fun edit() {
        setExtras(ResultListenerParams(DIALOG_ACTION, KEY_COMMENT_EDIT, true))
        backTo()
    }

    fun delete() {
        sendRequest(
            loading = true,
            request = {
                recipeRepository.deleteComment(
                    recipeId = recipeId,
                    commentId = commentId
                )
            },
            success = {
                showMessageWithRes(R.string.success_comment_delete)
                setExtras(ResultListenerParams(DIALOG_ACTION, KEY_COMMENT_DELETE, true))
                backTo()
            }
        )
    }

    companion object {
        private const val COMMENT_ID = "commentId"
        private const val RECIPE_ID = "recipeId"
    }
}
