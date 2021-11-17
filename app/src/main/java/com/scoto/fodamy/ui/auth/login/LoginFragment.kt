package com.scoto.fodamy.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentLoginBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    R.layout.fragment_login
) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, { state ->
            if (state) {
                navigateTo(actionLoginToHome)
            }
        })

        requiredFiledObserver()
        setSpannableText()

        textOnClickNavigateToDirections()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

    }

    private fun textOnClickNavigateToDirections() {
        binding.apply {
            tvDontHaveAccount.onClick { navigateTo(actionLoginToRegister) }
            tvForgottenPassword.onClick { navigateTo(actionLoginToResetPassword) }
        }
    }


    private fun setSpannableText() {
        binding.tvDontHaveAccount.text =
            getString(R.string.signup).spannableText(getString(R.string.dont_have_account))

    }


    private fun requiredFiledObserver() {
        viewModel.requiredFieldWarning.observe(viewLifecycleOwner, {
            when (it) {
                is InputErrorType.Email -> {
                    showRequiredField(getString(R.string.required_field_username))
                }
                is InputErrorType.Password -> {
                    showRequiredField(getString(R.string.required_field_password))
                }
                is InputErrorType.InvalidInputs -> {
                    showRequiredField(it.message)
                }
            }
        })
    }

    private fun showRequiredField(fieldText: String) {
        binding.included.tvRequiredField.apply {
            text = fieldText
            visibility = View.VISIBLE
        }
    }


    private fun navigateTo(directions: NavDirections) {
        val navController = findNavController()
        navController.navigate(directions)
    }

    companion object {
        private const val TAG = "LoginFragment"
        private val actionLoginToHome: NavDirections =
            LoginFragmentDirections.actionLoginFragment2ToHomeFragment()

        private val actionLoginToResetPassword: NavDirections =
            LoginFragmentDirections.actionLoginFragment2ToResetPasswordFragment2()

        private val actionLoginToRegister: NavDirections =
            LoginFragmentDirections.actionLoginFragment2ToRegisterFragment2()
    }
}