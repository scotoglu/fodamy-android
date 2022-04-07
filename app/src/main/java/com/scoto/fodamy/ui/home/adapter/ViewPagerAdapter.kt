package com.scoto.fodamy.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.scoto.fodamy.ui.home.editor_choices.EditorChoiceFragment
import com.scoto.fodamy.ui.home.last_added.LastAddedFragment

class ViewPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return SCREEN_NUMBER
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            SCREEN_EDITOR_CHOICE_POSITION -> {
                EditorChoiceFragment()
            }
            SCREEN_LAST_ADDED_POSITION -> {
                LastAddedFragment()
            }
            else -> {
                EditorChoiceFragment()
            }
        }
    }

    companion object {
        const val SCREEN_EDITOR_CHOICE_POSITION = 0
        const val SCREEN_LAST_ADDED_POSITION = 1
        const val SCREEN_NUMBER = 2
    }
}
