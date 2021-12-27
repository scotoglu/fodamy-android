package com.scoto.fodamy.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentHomeBinding
import com.scoto.fodamy.ext.addVerticalLineToTabs
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.isLogin()

        setupViewPagerAndTabLayout()
        eventObserver()
    }

    private fun setupViewPagerAndTabLayout() {
        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                ViewPagerAdapter.SCREEN_EDITOR_CHOICE_POSITION -> getString(R.string.tab_editor_choice)
                ViewPagerAdapter.SCREEN_LAST_ADDED_POSITION -> getString(R.string.tab_recently_added)
                else -> getString(R.string.tab_empty)
            }
        }.attach()

        // adds vertical divider to beween tabs in tabLayout.
        binding.tabLayout.addVerticalLineToTabs()
    }

    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UIHomeEvent.NavigateTo -> navigateTo(event.direction)
                is UIHomeEvent.IsLogin -> binding.customToolbar.setEndIconVisibility(event.isLogin)
                is UIHomeEvent.ShowMessage.Success -> {
                    binding.root.snackbar(event.message)
                    binding.customToolbar.setEndIconVisibility(false)
                }
                is UIHomeEvent.ShowMessage.Error -> binding.root.snackbar(event.message)
            }
        })
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}
