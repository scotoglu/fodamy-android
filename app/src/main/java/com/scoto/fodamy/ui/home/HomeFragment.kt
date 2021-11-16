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


        val fragmentManager = requireActivity().supportFragmentManager

        val adapter = ViewPagerAdapter(fragmentManager, viewLifecycleOwner)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.tab_editor_choice)
                }
                1 -> {
                    tab.text = getString(R.string.tab_recently_added)
                }
            }
        }.attach()

        viewModel.getEditorChoices()

    }

}