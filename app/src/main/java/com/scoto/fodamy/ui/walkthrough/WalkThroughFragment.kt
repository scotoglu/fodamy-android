package com.scoto.fodamy.ui.walkthrough

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentWalkThroughBinding
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.walkthrough.walkthrough_pages.WalkThroughAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WalkThroughFragment : BaseFragment<FragmentWalkThroughBinding>(
    R.layout.fragment_walk_through
) {

    private lateinit var titles: Array<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titles = requireContext().resources.getStringArray(R.array.walkthrouh_title)
        val walkThroughAdapter = WalkThroughAdapter(activity as AppCompatActivity, titles.size)
        binding.viewPager.adapter = walkThroughAdapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicators(position)
                changeButtonText(position)
            }
        })

        binding.ivClose.setOnClickListener {
            navigateTo()
        }

        binding.btnMoveNext.setOnClickListener {
            moveToNext(binding.viewPager.currentItem)
        }
    }

    private fun moveToNext(position: Int) {
        if (position != titles.size - 1) {
            binding.viewPager.currentItem = position + 1
            updateIndicators(position + 1)
        } else {
            navigateTo()
        }
    }

    private fun navigateTo() {
        val navController = findNavController()
        navController.navigate(
            WalkThroughFragmentDirections.actionWalkThroughFragmentToBottomNavHome()
        )
    }

    private fun changeButtonText(position: Int) {
        if (position == titles.size - 1) {
            binding.btnMoveNext.setText(R.string.btn_start)
        } else {
            binding.btnMoveNext.setText(R.string.btn_move_next)
        }
    }


    private fun updateIndicators(position: Int) {
        when (position) {
            0 -> {
                binding.apply {
                    ivIndicator.setImageResource(R.drawable.indicator_active)
                    ivIndicator2.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator3.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator4.setImageResource(R.drawable.indicator_inactive)
                }
            }
            1 -> {
                binding.apply {
                    ivIndicator.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator2.setImageResource(R.drawable.indicator_active)
                    ivIndicator3.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator4.setImageResource(R.drawable.indicator_inactive)
                }
            }
            2 -> {
                binding.apply {
                    ivIndicator.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator2.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator3.setImageResource(R.drawable.indicator_active)
                    ivIndicator4.setImageResource(R.drawable.indicator_inactive)
                }
            }
            3 -> {
                binding.apply {
                    ivIndicator.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator2.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator3.setImageResource(R.drawable.indicator_inactive)
                    ivIndicator4.setImageResource(R.drawable.indicator_active)
                }
            }
        }
    }

    companion object {
        private const val TAG = "WalkThroughFragment"
    }
}