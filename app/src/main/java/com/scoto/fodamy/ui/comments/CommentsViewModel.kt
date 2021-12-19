package com.scoto.fodamy.ui.comments

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.Comment
import com.scoto.fodamy.network.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _comments: MutableLiveData<PagingData<Comment>> = MutableLiveData()
    val comments: LiveData<PagingData<Comment>> get() = _comments

    val event: MutableLiveData<UICommentEvent> = MutableLiveData()

    var isUserComment: Boolean = false

    val editableComment: MutableLiveData<String> = MutableLiveData()
    val commentId: SingleLiveEvent<Int> = SingleLiveEvent()

    // Layout edittext text
    val comment = MutableLiveData<String>()

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

    fun isUserComment(commentUserId: Int) = viewModelScope.launch {
        isUserComment = dataStoreManager.isUserComment(commentUserId)
    }

    fun onSendClick() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = recipeRepository.sendComment(recipeId, comment.value.toString())) {
                is NetworkResponse.Success -> {
                    event.value = UICommentEvent.ShowMessage.SuccessMessage("Yorum Eklendi")
                    comment.value = ""
                }
                is NetworkResponse.Error -> {
                    event.value =
                        UICommentEvent.ShowMessage.ErrorMessage(response.exception.handleException())
                }
            }
        } else {
            event.value = UICommentEvent.OpenDialog(R.id.action_global_authDialog)
        }
    }

    fun onBackClick() {
        event.value = UICommentEvent.BackTo
    }

    fun onSaveClick() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            val response =
                recipeRepository.editComment(
                    recipeId = recipeId,
                    commentId = commentId.value!!,
                    text = editableComment.value!!
                )
            when (response) {
                is NetworkResponse.Error -> {
                    event.value =
                        UICommentEvent.ShowMessage.ErrorMessage(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    event.value = UICommentEvent.CommentEdited(response.data.message)
                }
            }
        }
    }

    fun onDelete() = viewModelScope.launch {
        when (
            val response =
                recipeRepository.deleteComment(recipeId = recipeId, commentId = commentId.value!!)
        ) {
            is NetworkResponse.Error -> {
                event.value =
                    UICommentEvent.ShowMessage.ErrorMessage(response.exception.handleException())
            }
            is NetworkResponse.Success -> {
                event.value = UICommentEvent.ShowMessage.SuccessMessage(response.data.message)
            }
        }
    }

    companion object {
        private const val TAG = "CommentsViewModel"
    }
}
