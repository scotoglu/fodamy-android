package com.scoto.fodamy.ui.favorites.recipe_details.image_popup

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentImagePopupBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePopupFragment :
    BaseFragment<FragmentImagePopupBinding, ImagePopupViewModel>(R.layout.fragment_image_popup) {
    override fun getViewModel(): Class<ImagePopupViewModel> = ImagePopupViewModel::class.java
}