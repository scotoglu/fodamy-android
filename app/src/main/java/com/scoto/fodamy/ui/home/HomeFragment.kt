package com.scoto.fodamy.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentHomeBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {


    private val viewModel: HomeViewModel by viewModels()


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
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}