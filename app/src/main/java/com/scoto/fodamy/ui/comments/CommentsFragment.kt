package com.scoto.fodamy.ui.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentCommentsBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsFragment :
    BaseFragment<FragmentCommentsBinding, CommentsViewModel>(R.layout.fragment_comments) {
    override fun getViewModel(): Class<CommentsViewModel> = CommentsViewModel::class.java
}