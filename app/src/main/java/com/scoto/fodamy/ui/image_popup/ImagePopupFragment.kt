package com.scoto.fodamy.ui.image_popup

import androidx.appcompat.app.AppCompatActivity
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentImagePopupBinding
import com.scoto.fodamy.ext.hideSystemUI
import com.scoto.fodamy.ext.showSystemUI
import com.scoto.fodamy.ui.base.BaseFragment_V2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePopupFragment :
    BaseFragment_V2<FragmentImagePopupBinding, ImagePopupViewModel>(R.layout.fragment_image_popup) {

    override fun initViews() {
        super.initViews()

        // hides statusbar and navigationbars
        (activity as AppCompatActivity).hideSystemUI(binding.root)

        val sliderAdapter = SliderAdapter(viewModel.getImages())
        binding.viewpagerSlider.adapter = sliderAdapter
        binding.dotsIndicator.setViewPager2(binding.viewpagerSlider)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // shows statusbar and navigationbars
        (activity as AppCompatActivity).showSystemUI(binding.root)
    }
}
