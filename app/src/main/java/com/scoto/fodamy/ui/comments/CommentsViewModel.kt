package com.scoto.fodamy.ui.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.Comment
import com.scoto.fodamy.network.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
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

    val viewState: SingleLiveEvent<CommentViewState> = SingleLiveEvent()

    // Holds the comment that will be edited.
    val editableComment: MutableLiveData<String> = MutableLiveData()

    // Checks the edit mode available or not
    val editMode = MutableLiveData(false)

    // used in delete
    private val commentId: SingleLiveEvent<Int> = SingleLiveEvent()

    // Holds the comment that user will be add
    val comment = MutableLiveData<String>()

    // gets passed arguments from recipe details fragment
    private val recipeId: Int = savedStateHandle.get<Int>("RECIPE_ID") ?: 0

    init {
        getComments()
    }

    private fun getComments() = viewModelScope.launch {
        recipeId.let {
            recipeRepository.getRecipeComments(it).collect { pagingData ->
                _comments.value = pagingData
            }
        }
    }

    fun onSend() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = recipeRepository.sendComment(recipeId, comment.value.toString())) {
                is NetworkResponse.Success -> {
                    viewState.value = CommentViewState.Success("Yorum Eklendi")
                    comment.value = ""
                }
                is NetworkResponse.Error -> {
                    showMessage(response.exception.handleException())
                }
            }
        } else {
            openDialog(R.id.action_global_authDialog)
        }
    }

    fun onBack() {
        if (editMode.value == true)
            editMode.value = false
        else
            backTo()
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
            val response =
                recipeRepository.editComment(
                    recipeId = recipeId,
                    commentId = commentId.value!!,
                    text = editableComment.value!!
                )
            when (response) {
                is NetworkResponse.Error -> {
                    showMessage(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    viewState.value = CommentViewState.CommentEdited(response.data.message)
                    setEditMode(false)
                }
            }
        } else {
            openDialog(R.id.action_global_authDialog)
        }
    }

    fun onDelete() = viewModelScope.launch {
        when (
            val response =
                recipeRepository.deleteComment(recipeId = recipeId, commentId = commentId.value!!)
        ) {
            is NetworkResponse.Error -> {
                showMessage(response.exception.handleException())
            }
            is NetworkResponse.Success -> {
                viewState.value = CommentViewState.Success(response.data.message)
            }
        }
    }

    companion object {
        private const val TAG = "CommentsViewModel"
    }
}
