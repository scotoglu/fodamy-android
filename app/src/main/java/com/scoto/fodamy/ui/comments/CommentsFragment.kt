package com.scoto.fodamy.ui.comments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCommentsBinding
import com.scoto.fodamy.ext.hideSoftKeyboard
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

        commentsAdapter.onItemLongClicked = { comment ->
            viewModel.isUserComment(comment.user.id)
            if (viewModel.isUserComment) {
                viewModel.editableComment.value = comment.text
                viewModel.commentId.value = comment.id
                navigateTo(CommentsFragmentDirections.actionCommentsFragmentToCommentDialog())
            }
        }
        getDialogAction()
        adapterLoadStateListener()
    }


    private fun adapterLoadStateListener() {
        commentsAdapter.addLoadStateListener { loadState ->
            binding.progressbar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.tvLoading.isVisible = loadState.source.refresh is LoadState.Loading
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
        //TODO(reduce the event type )
        //TODO(scroll to top of recyclerview after new comment added.)
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UICommentEvent.NavigateTo -> {
                    navigateTo(event.directions)
                }
                is UICommentEvent.BackTo -> {
                    if (binding.editMode == true) {
                        binding.editMode = false
                    } else {
                        findNavController().popBackStack()

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
        binding.rvComments.apply {
            setHasFixedSize(true)
            adapter = commentsAdapter
        }
    }


    private fun navigateTo(directions: NavDirections) {
        val navController = findNavController()
        navController.navigate(directions)
    }

    companion object {
        private const val TAG = "CommentsFragment"
    }
}