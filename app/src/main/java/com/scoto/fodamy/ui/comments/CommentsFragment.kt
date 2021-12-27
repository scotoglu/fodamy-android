package com.scoto.fodamy.ui.comments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCommentsBinding
import com.scoto.fodamy.ext.hideSoftKeyboard
import com.scoto.fodamy.ext.showIme
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.comments.adapter.CommentsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment :
    BaseFragment<FragmentCommentsBinding>(R.layout.fragment_comments) {

    private val viewModel: CommentsViewModel by viewModels()

    private lateinit var commentsAdapter: CommentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRvComments()
        eventObserver()

        binding.editMode = false

        viewModel.comments.observe(viewLifecycleOwner, {
            commentsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        adapterItemClicks()
        setFocusToAddCommentEdittext()
        getDialogAction()
        adapterLoadStateListener()
    }

    private fun adapterItemClicks() {
        commentsAdapter.onItemLongClicked = { comment ->
            viewModel.isUserComment(comment.user.id)
            if (viewModel.isUserComment) {
                viewModel.editableComment.value = comment.text
                viewModel.commentId.value = comment.id
                navigateTo(CommentsFragmentDirections.actionCommentsFragmentToCommentDialog())
            }
        }
    }

    private fun adapterLoadStateListener() {
        commentsAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvComments.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }
    }

    private fun getDialogAction() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("DialogAction")
            ?.observe(viewLifecycleOwner) { action ->
                when (action) {
                    "EDIT" -> {
                        binding.editMode = true
                    }
                    "DELETE" -> {
                        deleteComment()
                    }
                    else -> {}
                }
            }
    }

    private fun deleteComment() {
        viewModel.onDelete()
    }

    private fun eventObserver() {
        // TODO(reduce the event type )
        // TODO(scroll to top of recyclerview after new comment added.)
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UICommentEvent.NavigateTo -> {
                    navigateTo(event.directions)
                }
                is UICommentEvent.BackTo -> {
                    if (binding.editMode == true) {
                        binding.editMode = false
                    } else {
                        backTo()
                    }
                }
                is UICommentEvent.ShowMessage.SuccessMessage -> {
                    view?.snackbar(event.message)
                    context?.hideSoftKeyboard(binding.root)
                    requireView().clearFocus()
                    commentsAdapter.refresh()
                }
                is UICommentEvent.ShowMessage.ErrorMessage -> {
                    view?.snackbar(event.message)
                }
                is UICommentEvent.OpenDialog -> {
                    findNavController().navigate(event.actionId)
                }
                is UICommentEvent.CommentEdited -> {
                    view?.snackbar(event.message)
                    context?.hideSoftKeyboard(binding.root)
                    requireView().clearFocus()
                    commentsAdapter.refresh()
                    binding.editMode = false
                }
            }
        })
    }

    private fun setupRvComments() {
        commentsAdapter = CommentsAdapter()
        binding.adapter = commentsAdapter
        binding.rvComments.apply {
            setHasFixedSize(true)
            adapter = commentsAdapter
        }
    }

    private fun setFocusToAddCommentEdittext() {
        (activity as AppCompatActivity).showIme()
        binding.etAddComment.requestFocus()
    }

    companion object {
        private const val TAG = "CommentsFragment"
    }
}
