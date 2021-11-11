package com.scoto.fodamy.ui.favorites.recipe_details.image_popup

import androidx.fragment.app.viewModels
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentImagePopupBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePopupFragment :
    BaseFragment<FragmentImagePopupBinding>(R.layout.fragment_image_popup) {
    private val viewMode: ImagePopupViewModel by viewModels()
}