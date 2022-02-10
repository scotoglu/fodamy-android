package com.scoto.fodamy.ui.comments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCommentsBinding
import com.scoto.fodamy.ext.hideSoftKeyboard
import com.scoto.fodamy.ext.showIme
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.comments.adapter.CommentsAdapter
import com.scoto.fodamy.util.DIALOG_ACTION
import com.scoto.fodamy.util.KEY_COMMENT_DELETE
import com.scoto.fodamy.util.KEY_COMMENT_EDIT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment :
    BaseFragment<FragmentCommentsBinding, CommentsViewModel>(R.layout.fragment_comments) {

    private lateinit var commentsAdapter: CommentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFocusToAddCommentEdittext()

        setFragmentResultListener(DIALOG_ACTION) { _, bundle ->
            val resultDelete = bundle.get(KEY_COMMENT_DELETE)
            val resultEdit = bundle.get(KEY_COMMENT_EDIT)
            if (resultDelete != null && resultDelete as Boolean) {
                commentsAdapter.refresh()
            }
            if (resultEdit != null && resultEdit as Boolean) {
                viewModel.setEditMode(true)
            }
        }
    }

    override fun registerObservables() {
        super.registerObservables()
        commentObserver()
        eventObserver()
    }

    private fun commentObserver() {
        viewModel.comments.observe(viewLifecycleOwner) {
            commentsAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
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
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                CommentEvent.Success -> {
                    context?.hideSoftKeyboard(binding.root)
                    requireView().clearFocus()
                    commentsAdapter.refresh()
                }
            }
        }
    }

    private fun setFocusToAddCommentEdittext() {
        (activity as AppCompatActivity).showIme()
        binding.etAddComment.requestFocus()
    }
}
