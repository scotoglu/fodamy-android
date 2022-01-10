package com.scoto.fodamy.ui.home

import android.os.Bundle
import android.view.View
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
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val isSharedViewModel: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPagerAndTabLayout()
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

    override fun registerObservables() {
        super.registerObservables()
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is HomeViewEvent.IsLogin -> binding.customToolbar.setEndIconVisibility(event.isLogin)
                is HomeViewEvent.Success -> {
                    binding.root.snackbar(event.message)
                    binding.customToolbar.setEndIconVisibility(false)
                }
            }
        })
    }
}
