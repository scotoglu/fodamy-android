package com.scoto.fodamy.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.scoto.fodamy.ui.home.pages.PagesOfTab

class ViewPagerAdapter(
    private val fm: FragmentManager,
    private val lifeCycle: LifecycleOwner
) : FragmentStateAdapter(fm, lifeCycle.lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                PagesOfTab()
            }
            1 -> {
                PagesOfTab()
            }
            else -> {
                Fragment()
            }
        }

    }
}