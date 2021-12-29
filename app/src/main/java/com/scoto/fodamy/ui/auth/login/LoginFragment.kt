package com.scoto.fodamy.ui.auth.login

import android.os.Bundle
import android.view.View
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentLoginBinding
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpannableText()
    }

    private fun setSpannableText() {
        binding.tvDontHaveAccount.text =
            getString(R.string.signup).spannableText(getString(R.string.dont_have_account))
    }

    override fun registerObservables() {
        super.registerObservables()
        viewModel.requiredFieldWarning.observe(viewLifecycleOwner, { errorType ->
            when (errorType) {
                InputErrorType.Username -> {
                    showRequiredField(getString(R.string.required_field_username))
                }
                InputErrorType.Password -> {
                    showRequiredField(getString(R.string.required_field_password))
                }
            }
        })
    }

    private fun showRequiredField(fieldText: String) {
        binding.included.apply {
            text = fieldText
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}
