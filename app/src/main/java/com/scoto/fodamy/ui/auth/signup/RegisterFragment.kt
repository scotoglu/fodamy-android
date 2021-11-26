package com.scoto.fodamy.ui.auth.signup

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRegisterBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.auth.UIAuthEvent
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpannableText()

        viewModel.state.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UIAuthEvent.NavigateTo -> {
                    event.directions?.let { navigateTo(it) }
                    view.snackbar(event.message!!)
                }
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
                is InputErrorType.ShowMessage -> {
                    showRequiredField(it.message)
                }
                InputErrorType.CloseMessage -> {
                    binding.included.tvRequiredField.isVisible = false
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
        binding.tvHaveAccount.text =
            getString(R.string.login).spannableText(getString(R.string.have_account))

    }

}