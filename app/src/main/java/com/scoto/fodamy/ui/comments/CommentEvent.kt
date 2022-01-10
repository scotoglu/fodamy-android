package com.scoto.fodamy.ui.comments

sealed class CommentEvent {
    object Success : CommentEvent()
    data class CommentEdited(val message: String) : CommentEvent()
}
