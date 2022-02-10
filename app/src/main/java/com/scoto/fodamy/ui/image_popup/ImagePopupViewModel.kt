package com.scoto.fodamy.ui.image_popup

import android.os.Bundle
import com.scoto.domain.models.Image
import com.scoto.domain.models.ImageList
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagePopupViewModel @Inject constructor() : BaseViewModel() {

    private lateinit var images: ImageList
    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        with(ImagePopupFragmentArgs.fromBundle(bundle)) {
            this@ImagePopupViewModel.images = images
        }
    }

    fun getImages(): ImageList {
        val populatedImages = mutableListOf<Image>()
        if (images.images.size == 1) {
            val image = images.images[0]
            for (i in 0 until 4) {
                populatedImages.add(image)
            }
        }

        return ImageList(populatedImages)
    }
}
