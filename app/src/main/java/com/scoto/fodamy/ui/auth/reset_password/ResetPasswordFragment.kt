package com.scoto.fodamy.ui.auth.reset_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentResetPasswordBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding, ResetPasswordViewModel>(R.layout.fragment_reset_password) {
    override fun getViewModel(): Class<ResetPasswordViewModel> = ResetPasswordViewModel::class.java
}