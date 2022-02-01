package com.scoto.fodamy.ui.dialog.unfollow

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.DialogUnfollowBinding
import com.scoto.fodamy.ui.dialog.base.BaseBottomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnfollowDialog :
    BaseBottomDialog<DialogUnfollowBinding, UnfollowViewModel>(R.layout.dialog_unfollow)
