package com.scoto.fodamy.ui.comments

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Comment
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.R
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.util.paging_sources.CommentPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    private val _comments: MutableLiveData<PagingData<Comment>> = MutableLiveData()
    val comments: LiveData<PagingData<Comment>> get() = _comments

    val event: SingleLiveEvent<CommentEvent> = SingleLiveEvent()

    // Holds the comment that will be edited.
    val editableComment: MutableLiveData<String> = MutableLiveData()

    // Checks the edit mode available or not
    val editMode = MutableLiveData(false)

    // used in delete
    private val commentId: SingleLiveEvent<Int> = SingleLiveEvent()

    // Holds the comment that user will be add
    val comment = MutableLiveData<String>()

    // gets passed arguments from recipe details fragment
    private var recipeId: Int = -1

    init {
        isLogin()
    }

    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        recipeId = CommentsFragmentArgs.fromBundle(bundle).recipeid
        getComments()
    }

    private fun isLogin() = viewModelScope.launch {
        if (!dataStoreManager.isLogin()) {
            openNavigationDilog(R.id.action_global_authDialog)
        }
    }

    private fun getComments() {
        sendRequest(
            request = {
                Pager(
                    config = pageConfig,
                    pagingSourceFactory = { CommentPagingSource(recipeRepository, recipeId) }
                ).flow
            },
            success = {
                viewModelScope.launch {
                    it.cachedIn(viewModelScope).collect {
                        _comments.value = it
                    }
                }
            }
        )
    }

    fun onSend() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                request = { recipeRepository.sendComment(recipeId, comment.value.toString()) },
                success = {
                    comment.value = ""
                    showMessageWithRes(R.string.success_comment_add)
                    event.value = CommentEvent.Success
                }
            )
        } else {
            openNavigationDilog(R.id.action_global_authDialog)
        }
    }

    override fun backTo() {
        if (editMode.value == true)
            editMode.value = false
        else
            super.backTo()
    }

    fun onEdit(comment: Comment) = viewModelScope.launch {
        if (dataStoreManager.isUserComment(comment.user.id)) {
            navigate(
                CommentsFragmentDirections.actionCommentsFragmentToCommentDialog(
                    comment.id,
                    recipeId
                )
            )
            editableComment.value = comment.text
            commentId.value = comment.id
        }
    }

    fun setEditMode(isEditMode: Boolean) {
        editMode.value = isEditMode
    }

    fun onUpdate() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                request = {
                    recipeRepository.editComment(
                        recipeId = recipeId,
                        commentId = commentId.value!!,
                        text = editableComment.value.toString()
                    )
                },
                success = {
                    setEditMode(false)
                    event.value = CommentEvent.Success
                    showMessageWithRes(R.string.success_comment_edit)
                }
            )
        } else {
            openNavigationDilog(R.id.action_global_authDialog)
        }
    }

    fun onDelete() = viewModelScope.launch {
        sendRequest(
            request = { recipeRepository.deleteComment(recipeId, commentId.value!!) },
            success = {
                showMessageWithRes(R.string.success_comment_delete)
                event.value = CommentEvent.Success
            }
        )
    }

    companion object {
        private val pageConfig = PagingConfig(
            pageSize = 24,
            maxSize = 100,
            enablePlaceholders = false
        )
    }
}
