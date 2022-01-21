package com.scoto.fodamy.ui.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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
    private val dataStoreManager: DataStoreManager,
    private val savedStateHandle: SavedStateHandle
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
    private val recipeId: Int = savedStateHandle.get<Int>(RECIPE_ID) ?: 1

    init {
        getComments()
    }

    fun getComments() = viewModelScope.launch {
        sendRequest(
            success = {
                val pager = Pager(
                    config = pageConfig,
                    pagingSourceFactory = { CommentPagingSource(recipeRepository, recipeId) }).flow
                pager.cachedIn(viewModelScope).collect {
                    _comments.value = it
                }
            }
        )
    }

    fun onSend() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                success = {
                    recipeRepository.sendComment(recipeId, comment.value.toString())
                    comment.value = ""
                    showMessageWithRes(R.string.success_comment_add)
                }
            )
        } else {
            openDialog(R.id.action_global_authDialog)
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
            navigate(CommentsFragmentDirections.actionCommentsFragmentToCommentDialog())
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
                success = {
                    recipeRepository.editComment(
                        recipeId = recipeId,
                        commentId = commentId.value!!,
                        text = editableComment.value.toString()
                    )
                    setEditMode(false)
                    showMessageWithRes(R.string.success_comment_edit)
                }
            )
        } else {
            openDialog(R.id.action_global_authDialog)
        }
    }

    fun onDelete() = viewModelScope.launch {
        sendRequest(
            success = {
                recipeRepository.deleteComment(recipeId, commentId.value!!)
                showMessageWithRes(R.string.success_comment_delete)
            }
        )
    }

    companion object {
        private val pageConfig = PagingConfig(
            pageSize = 24,
            maxSize = 100,
            enablePlaceholders = false
        )
        private const val RECIPE_ID = "RECIPE_ID"
    }
}
