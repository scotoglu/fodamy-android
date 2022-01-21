package com.scoto.fodamy.ui.comments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCommentsBinding
import com.scoto.fodamy.ext.hideSoftKeyboard
import com.scoto.fodamy.ext.showIme
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.comments.adapter.CommentsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment :
    BaseFragment<FragmentCommentsBinding, CommentsViewModel>(R.layout.fragment_comments) {

    private lateinit var commentsAdapter: CommentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // After comment add/edit/delete, sends request again to update
        viewModel.getComments()

        setFocusToAddCommentEdittext()
        getDialogAction()
    }

    override fun registerObservables() {
        super.registerObservables()
        commentObserver()
        eventObserver()
    }

    private fun commentObserver() {
        viewModel.comments.observe(viewLifecycleOwner, {
            commentsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    override fun addItemClicks() {
        super.addItemClicks()
        commentsAdapter.onItemLongClicked = { comment ->
            viewModel.onEdit(comment)
        }
    }

    override fun addAdapterLoadStateListener() {
        super.addAdapterLoadStateListener()
        commentsAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
                rvComments.isVisible = loadState.source.refresh is LoadState.NotLoading
            }
        }
    }

    // TODO("handle in BaseFragment")
    private fun getDialogAction() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            DIALOG_ACTION
        )
            ?.observe(viewLifecycleOwner) { action ->
                when (action) {
                    EDIT -> {
                        viewModel.setEditMode(true)
                    }
                    DELETE -> {
                        viewModel.onDelete()
                    }
                }
            }
    }

    override fun initViews() {
        super.initViews()
        commentsAdapter = CommentsAdapter()
        binding.adapter = commentsAdapter
        binding.rvComments.apply {
            setHasFixedSize(true)
            adapter = commentsAdapter
        }
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is CommentEvent.Success -> {
                    context?.hideSoftKeyboard(binding.root)
                    requireView().clearFocus()
                    commentsAdapter.refresh()
                }

                is CommentEvent.CommentEdited -> {
                    context?.hideSoftKeyboard(binding.root)
                    requireView().clearFocus()
                    commentsAdapter.refresh()
                }
            }
        })
    }

    private fun setFocusToAddCommentEdittext() {
        (activity as AppCompatActivity).showIme()
        binding.etAddComment.requestFocus()
    }

    companion object {
        private const val DIALOG_ACTION = "DialogAction"
        private const val EDIT = "EDIT"
        private const val DELETE = "DELETE"
    }
}
