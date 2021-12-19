package com.scoto.fodamy.ui.home.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.scoto.fodamy.ui.home.pages.PagesOfTab
import com.scoto.fodamy.util.FROM_EDITOR_CHOICE
import com.scoto.fodamy.util.FROM_LAST_ADDED

class ViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return SCREEN_NUMBER
    }

    override fun createFragment(position: Int): Fragment {

        val fragment = PagesOfTab()

        return when (position) {
            SCREEN_EDITOR_CHOICE_POSITION -> {
                fragment.arguments = bundleOf(REQUEST_TYPE to FROM_EDITOR_CHOICE)
                fragment
            }
            SCREEN_LAST_ADDED_POSITION -> {
                fragment.arguments = bundleOf(REQUEST_TYPE to FROM_LAST_ADDED)
                fragment
            }
            else -> {
                fragment.arguments = bundleOf(REQUEST_TYPE to FROM_EDITOR_CHOICE)
                fragment
            }
        }
    }

    companion object {
        const val SCREEN_EDITOR_CHOICE_POSITION = 0
        const val SCREEN_LAST_ADDED_POSITION = 1
        const val SCREEN_NUMBER = 2
        const val REQUEST_TYPE = "REQUEST_TYPE"
    }
}
