package com.scoto.fodamy.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentProfileBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.12.2021 at 14:15
 */
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    R.layout.fragment_profile
) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.user.observe(viewLifecycleOwner, { user ->
            binding.user = user
        })

        eventObserver()
        endIconObserver()
    }

    private fun eventObserver() {

        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is UIProfileEvent.ShowMessage -> {
                    view?.snackbar(event.message)
                }
                is UIProfileEvent.NavigateTo -> {
                    val navController = findNavController()
                    navController.navigate(event.actionId)
                }
            }
        }
    }

    private fun endIconObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.isLoginLiveData().observe(viewLifecycleOwner, {
                if (it.isNotBlank()) {
                    val drawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_logout_2)
                    binding.customToolbar.setEndIcon(drawable)
                }
            })
        }
    }

    private fun navigateTo() {
    }
}
