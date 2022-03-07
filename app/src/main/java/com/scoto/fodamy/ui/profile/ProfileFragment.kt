package com.scoto.fodamy.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentProfileBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.12.2021 at 14:15
 */
@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>(
    R.layout.fragment_profile
) {

    private lateinit var profilePagingAdapter: ProfilePagingAdapter
    override val isSharedViewModel: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.isLogin()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun registerObservables() {
        super.registerObservables()
        eventObserver()
        userObserver()
        recipeObserver()
        tokenObserverForRefresh()
    }

    private fun recipeObserver() {
        viewModel.recipes.observe(viewLifecycleOwner) {
            profilePagingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun userObserver() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.user = user
        }
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

    override fun addAdapterLoadStateListener() {
        super.addAdapterLoadStateListener()
        profilePagingAdapter.addLoadStateListener { loadState ->
            binding.apply {
                customStateView.setLoadingState(loadState.source.refresh is LoadState.Loading)
                tvRecipes.isVisible = loadState.source.refresh !is LoadState.Loading
                tvLikes.isVisible = loadState.source.refresh !is LoadState.Loading
                customStateView.setErrorState(loadState.source.refresh is LoadState.Error)
            }
        }
    }

    override fun initViews() {
        super.initViews()
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
                is ProfileEvent.Success -> {
                    binding.apply {
                        tvLogin.isVisible = true
                        customToolbar.setEndIconVisibility(false)
                    }
                }
                is ProfileEvent.IsLogin -> {
                    binding.apply {
                        customToolbar.setEndIconVisibility(event.isLogin)
                        tvLogin.isVisible = !event.isLogin
                    }
                }
            }
        }
    }
}
