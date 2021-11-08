package com.scoto.fodamy.ui.auth.login

import android.os.Bundle
import android.view.View
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentLoginBinding
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.ext.withClickable
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDontHaveAccount.text =
            getString(R.string.signup).spannableText(getString(R.string.dont_have_account))


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

    }


    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java
}