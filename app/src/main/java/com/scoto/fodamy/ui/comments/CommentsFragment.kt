package com.scoto.fodamy.ui.comments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        setupRvComments()
        initAdapterLoadStateListener()
        eventObserver()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

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
    }

    private fun getDialogAction() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("DialogAction")
            ?.observe(viewLifecycleOwner) { action ->
                when (action) {
                    "EDIT" -> {
                        editComment()
                    }
                    "DELETE" -> {
                        deleteComment()
                    }
                    else -> {}
                }

            }
    }

    private fun editComment() {
        binding.apply {
            rvComments.isVisible = false
            etAddComment.isVisible = false
            btnAddComment.isVisible = false
            etEditComment.isVisible = true
            btnSave.isVisible = true
        }
    }

    private fun deleteComment() {
        viewModel.onDelete()
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UICommentEvent.NavigateTo -> {
                    navigateTo(event.directions)
                }
                is UICommentEvent.BackTo -> findNavController().popBackStack()
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

                    binding.apply {
                        rvComments.isVisible = true
                        etAddComment.isVisible = true
                        btnAddComment.isVisible = true
                        etEditComment.isVisible = false
                        btnSave.isVisible = false
                    }
                }
            }

        })
    }

    private fun setupRvComments() {
        commentsAdapter = CommentsAdapter()
        binding.rvComments.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = commentsAdapter
        }
    }

    private fun initAdapterLoadStateListener() {
        commentsAdapter.addLoadStateListener { loadState ->

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