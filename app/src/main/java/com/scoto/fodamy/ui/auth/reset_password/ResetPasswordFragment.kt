package com.scoto.fodamy.ui.auth.reset_password

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentResetPasswordBinding
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding, ResetPasswordViewModel>(R.layout.fragment_reset_password) {

    override fun registerObservables() {
        super.registerObservables()
        viewModel.requiredFieldWarning.observe(viewLifecycleOwner, { errorType ->
            when (errorType) {
                InputErrorType.Email -> {
                    showRequiredField(getString(R.string.required_field_email))
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
        private const val TAG = "ResetPasswordFragment"
    }
}
