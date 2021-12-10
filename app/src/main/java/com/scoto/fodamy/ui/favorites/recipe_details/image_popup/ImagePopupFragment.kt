package com.scoto.fodamy.ui.favorites.recipe_details.image_popup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentImagePopupBinding
import com.scoto.fodamy.ext.hideSystemUI
import com.scoto.fodamy.ext.showSystemUI
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePopupFragment :
    BaseFragment<FragmentImagePopupBinding>(R.layout.fragment_image_popup) {
    private val viewMode: ImagePopupViewModel by viewModels()
    private val args: ImagePopupFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //hides statusbar and navigationbars
        (activity as AppCompatActivity).hideSystemUI(binding.root)

        val images = args.images
        val sliderAdapter = SliderAdapter(images) {
            //TODO("Close the image slider fragment,fix this, use interface for readability")
            findNavController().popBackStack()
        }
        binding.viewpagerSlider.adapter = sliderAdapter

    }


    override fun onDestroyView() {
        super.onDestroyView()
        //shows statusbar and navigationbars
        (activity as AppCompatActivity).showSystemUI(binding.root)
    }
}
