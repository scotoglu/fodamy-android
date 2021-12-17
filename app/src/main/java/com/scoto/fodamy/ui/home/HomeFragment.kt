package com.scoto.fodamy.ui.home

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentHomeBinding
import com.scoto.fodamy.ext.snackbar
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.home.adapter.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {


    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setFragmentResultListener("loginControl") { requestKey, bundle ->
//            val result = bundle.getBoolean("isLogin")
//            val drawable =
//                ContextCompat.getDrawable(requireContext(), R.drawable.ic_logout_2)
//            if (result) binding.customToolbar.setEndIcon(drawable)
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.customToolbar.getEndIcon().onClick {
//            viewModel.logout()
//        }

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupViewPagerAndTabLayout()
        eventObserver()
        endIconObserver()

    }

    //TODO() remove and use buttons instead of tabLayout
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


        //adds vertical divider to beween tabs in tabLayout.
        val root: View? = binding.tabLayout.getChildAt(0)
        if (root is LinearLayout) {
            root.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.zircon
                )
            )
            drawable.setSize(4, 1)
            root.dividerPadding = 10
            root.dividerDrawable = drawable
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


    private fun eventObserver() {
        viewModel.event.observe(viewLifecycleOwner, { event ->
            when (event) {
                is UIHomeEvent.NavigateTo -> {
                    navigateTo(event.direction)
                }
                is UIHomeEvent.ShowMessage.Success -> {
                    val drawable =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_login)
                    binding.customToolbar.setEndIcon(drawable)
                    binding.root.snackbar(event.message)
                }
                is UIHomeEvent.ShowMessage.Error -> {
                    binding.root.snackbar(event.message)
                }
            }

        })
    }

    private fun navigateTo(directions: NavDirections) {
        val navController = findNavController()
        navController.navigate(directions)
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}