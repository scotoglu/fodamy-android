package com.scoto.fodamy.ui.auth.signup

import android.os.Bundle
import android.view.View
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRegisterBinding
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterViewModel>(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.tvHaveAccount.text =
            getString(R.string.login).spannableText(getString(R.string.have_account))


        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }


    override fun getViewModelClass(): Class<RegisterViewModel> = RegisterViewModel::class.java
}