package com.scoto.fodamy.ui.comments

sealed class CommentViewState {
    data class Success(val message: String) : CommentViewState()
    data class CommentEdited(val message: String) : CommentViewState()
    object EditModeAndBack : CommentViewState()
}
