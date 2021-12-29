package com.scoto.fodamy.ui.walkthrough

import androidx.viewpager2.widget.ViewPager2
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentWalkThroughBinding
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ui.base.BaseFragment
import com.scoto.fodamy.ui.walkthrough.adapter.WalkThroughAdapter
import com.scoto.fodamy.ui.walkthrough.model.Walkthrough
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalkThroughFragment : BaseFragment<FragmentWalkThroughBinding, WalkThroughViewModel>(
    R.layout.fragment_walk_through
) {

    private var contentSize: Int? = null

    override fun initViews() {
        super.initViews()
        contentSize = Walkthrough.getPrePopulatedData().size

        val walkThroughAdapter = WalkThroughAdapter(Walkthrough.getPrePopulatedData())
        binding.apply {
            viewPager.adapter = walkThroughAdapter
            indicator.setViewPager2(viewPager)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    changeButtonText(position)
                }
            })

            btnMoveNext.onClick {
                moveToNext(binding.viewPager.currentItem)
            }
        }
    }

    private fun moveToNext(position: Int) {
        if (position != contentSize?.minus(1)) {
            binding.viewPager.currentItem = position + 1
        } else {
            viewModel.toHome()
        }
    }

    private fun changeButtonText(position: Int) {

        if (position == contentSize?.minus(1)) {
            binding.btnMoveNext.setText(R.string.btn_start)
        } else {
            binding.btnMoveNext.setText(R.string.btn_move_next)
        }
    }

    companion object {
        private const val TAG = "WalkThroughFragment"
    }
}
