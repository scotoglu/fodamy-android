package com.scoto.fodamy.ui.auth.signup

import android.os.Bundle
import android.view.View
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRegisterBinding
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.base.BaseFragment_V2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment_V2<FragmentRegisterBinding, RegisterViewModel>(R.layout.fragment_register) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpannableText()

    }

    override fun registerObservables() {
        super.registerObservables()
        viewModel.requiredFieldWarning.observe(viewLifecycleOwner, {
            when (it) {
                InputErrorType.Email -> {
                    showRequiredField(getString(R.string.required_field_email))
                }
                InputErrorType.Password -> {
                    showRequiredField(getString(R.string.required_field_password_length))
                }
                InputErrorType.Username -> {
                    showRequiredField(getString(R.string.required_field_username))
                }
            }
        })
    }

    private fun showRequiredField(fieldText: String) {
        binding.included.apply {
            text = fieldText
        }
    }

    private fun setSpannableText() {
        binding.tvHaveAccount.text =
            getString(R.string.login).spannableText(getString(R.string.have_account))
    }
}
