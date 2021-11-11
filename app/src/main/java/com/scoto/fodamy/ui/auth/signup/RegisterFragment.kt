package com.scoto.fodamy.ui.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRegisterBinding
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpannableText()

        viewModel.state.observe(viewLifecycleOwner, { state ->
            if (state) {
                navigateTo(actionRegisterToLogin)
            }
        })

        requiredFieldObserver()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun requiredFieldObserver() {
        viewModel.requiredFieldWarning.observe(viewLifecycleOwner, {
            when (it) {
                is InputErrorType.Email -> {
                    showRequiredField(getString(R.string.required_field_email))
                }
                is InputErrorType.Password -> {
                    showRequiredField(getString(R.string.required_field_password))
                }
                is InputErrorType.Username -> {
                    showRequiredField(getString(R.string.required_field_username))
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

    private fun setSpannableText() {
        binding.tvHaveAccount.apply {
            text = getString(R.string.login).spannableText(getString(R.string.have_account))
            setOnClickListener { navigateTo(actionRegisterToLogin) }
        }
    }


    companion object {
        private val actionRegisterToLogin =
            RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment2()
    }
}