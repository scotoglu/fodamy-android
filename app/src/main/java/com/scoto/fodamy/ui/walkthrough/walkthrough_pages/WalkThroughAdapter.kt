package com.scoto.fodamy.ui.walkthrough.walkthrough_pages

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WalkThroughAdapter(
    activity: AppCompatActivity,
    private val itemsCount: Int
) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = itemsCount

    override fun createFragment(position: Int): Fragment = WalkthroughPagesFragment.getInstance(position)
}