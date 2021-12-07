package com.scoto.fodamy.ui.comments

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
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

    private val _event: MutableLiveData<UICommentEvent> = MutableLiveData()
    val event: LiveData<UICommentEvent> get() = _event


    //Layout edittext text
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


    fun onSendClick() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            val response = recipeRepository.sendComment(recipeId, comment.value.toString())
            when (response) {
                is NetworkResponse.Success -> {
                    _event.value = UICommentEvent.ShowMessage.SuccessMessage("Yorum Eklendi")
                    comment.value = ""
                }
                is NetworkResponse.Error -> {
                    _event.value =
                        UICommentEvent.ShowMessage.ErrorMessage(response.exception.handleException())
                }
            }

        } else {
            _event.value = UICommentEvent.OpenDialog(R.id.action_global_authDialog)
        }


    }

    companion object {
        private const val TAG = "CommentsViewModel"
    }

}
