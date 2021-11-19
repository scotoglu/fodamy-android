package com.scoto.fodamy.ui.home

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentHomeBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ext.toast
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {


    private val viewModel: HomeViewModel by viewModels()


    private lateinit var toolbarEndIcon: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarEndIcon = requireActivity().findViewById(R.id.toolbar_iv_end_icon)

        viewModel.token.observe(viewLifecycleOwner, { token ->
            if (token.isNotBlank()) {
                toolbarEndIcon.setImageResource(R.drawable.ic_logout_2)
            } else {
                toolbarEndIcon.setImageResource(R.drawable.ic_login)
            }
        })

        toolbarEndIcon.onClick {
            if (viewModel.token.value.toString().isNotBlank()) {
                logout()
            } else {
                navigateTo(actionHomeToLogin)
            }
        }

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


    private fun logout() {
        viewModel.logout()
        viewModel.state.observe(viewLifecycleOwner, {
            requireContext().toast(it)
        })
    }

    private fun navigateTo(directions: NavDirections) {
        val navController = findNavController()
        navController.navigate(directions)
    }


    companion object {
        private const val TAG = "HomeFragment"
        private val actionHomeToLogin =
            HomeFragmentDirections.actionHomeFragmentToLoginFragment2()
    }
}