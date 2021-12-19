package com.scoto.fodamy.ui.image_popup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentImagePopupBinding
import com.scoto.fodamy.ext.hideSystemUI
import com.scoto.fodamy.ext.onClick
import com.scoto.fodamy.ext.showSystemUI
import com.scoto.fodamy.network.models.Image
import com.scoto.fodamy.network.models.ImageList
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePopupFragment :
    BaseFragment<FragmentImagePopupBinding>(R.layout.fragment_image_popup) {
    private val viewMode: ImagePopupViewModel by viewModels()
    private val args: ImagePopupFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hides statusbar and navigationbars
        (activity as AppCompatActivity).hideSystemUI(binding.root)

        val images = args.images
        val populatedImages = mutableListOf<Image>()
        if (images.images.size == 1) {
            val image = images.images[0]
            for (i in 0 until 4) {
                populatedImages.add(image)
            }
        }

        val sliderAdapter = SliderAdapter(ImageList(populatedImages))
        binding.viewpagerSlider.adapter = sliderAdapter
        binding.dotsIndicator.setViewPager2(binding.viewpagerSlider)

        binding.ivClose.onClick {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // shows statusbar and navigationbars
        (activity as AppCompatActivity).showSystemUI(binding.root)
    }
}
