package com.scoto.fodamy.ui.comments

sealed class CommentEvent {
    data class Success(val message: String) : CommentEvent()
    data class CommentEdited(val message: String) : CommentEvent()
}
