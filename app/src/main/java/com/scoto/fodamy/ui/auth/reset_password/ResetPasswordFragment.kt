package com.scoto.fodamy.ui.auth.reset_password

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentResetPasswordBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.ui.auth.UIAuthEvent
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding>(R.layout.fragment_reset_password) {

    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UIAuthEvent.NavigateTo -> {
                    view.snackbar(event.message!!)
                }
            }
        })

        requiredFieldObserver()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun requiredFieldObserver() {
        viewModel.requiredFieldWarnings.observe(viewLifecycleOwner, {
            when (it) {
                is InputErrorType.Email -> {
                    showRequiredField(getString(R.string.required_field_email))
                }
                is InputErrorType.ShowMessage -> {
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

    companion object {
        private const val TAG = "ResetPasswordFragment"
    }
}
