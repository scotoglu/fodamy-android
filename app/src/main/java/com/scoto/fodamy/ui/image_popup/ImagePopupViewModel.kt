package com.scoto.fodamy.ui.image_popup

import androidx.lifecycle.SavedStateHandle
import com.scoto.fodamy.network.models.Image
import com.scoto.fodamy.network.models.ImageList
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagePopupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    fun getImages(): ImageList {
        val images = savedStateHandle.get<ImageList>(IMAGES)
        val populatedImages = mutableListOf<Image>()
        if (images?.images?.size == 1) {
            val image = images.images[0]
            for (i in 0 until 4) {
                populatedImages.add(image)
            }
        }

        return ImageList(populatedImages)
    }

    companion object {
        private const val IMAGES = "images"
    }
}
