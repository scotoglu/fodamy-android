package com.scoto.fodamy.ui.dialog.comment

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.DialogCommentBinding
import com.scoto.fodamy.ui.dialog.base.BaseBottomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentDialog :
    BaseBottomDialog<DialogCommentBinding, CommentDialogViewModel>(R.layout.dialog_comment)
