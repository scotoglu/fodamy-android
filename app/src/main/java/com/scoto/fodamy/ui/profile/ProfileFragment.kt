package com.scoto.fodamy.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
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
    private lateinit var profilePagingAdapter: ProfilePagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupRvs()
        viewModel.user.observe(viewLifecycleOwner, { user ->
            binding.user = user
        })

        viewModel.recipes.observe(viewLifecycleOwner) {
            profilePagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        tokenObserverForRefresh()
        adapterLoadStateListener()
        eventObserver()
    }

    private fun tokenObserverForRefresh() {
        lifecycleScope.launchWhenCreated {
            viewModel.isLoginLiveData().observe(viewLifecycleOwner) {
                if (!it.isNullOrBlank()) {
                    viewModel.getUserDetails()
                }
            }
        }
    }

    private fun adapterLoadStateListener() {
        profilePagingAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                tvRecipes.isVisible = loadState.source.refresh !is LoadState.Loading
                tvLikes.isVisible = loadState.source.refresh !is LoadState.Loading
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
            }
        }
    }

    private fun setupRvs() {
        profilePagingAdapter = ProfilePagingAdapter()
        binding.apply {
            rvRecipes.setHasFixedSize(true)
            rvRecipes.adapter = profilePagingAdapter
            rvLikes.setHasFixedSize(true)
            rvLikes.adapter = profilePagingAdapter
        }
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner) { event ->
            when (event) {
                is UIProfileEvent.ShowMessage.SuccessMessage -> {
                    view?.snackbar(event.message)
                    binding.tvLogin.isVisible = true
                }
                is UIProfileEvent.ShowMessage.ErrorMessage -> {
                    view?.snackbar(event.message)
                }
                is UIProfileEvent.NavigateTo -> {
                    val navController = findNavController()
                    event.actionId?.let { navController.navigate(it) }
                    event.direction?.let { navigateTo(it) }
                }
                is UIProfileEvent.IsLogin -> {
                    binding.apply {
                        customToolbar.setEndIconVisibility(event.isLogin)
                        tvLogin.isVisible = !event.isLogin
                    }
                }
            }
        }
    }
}
