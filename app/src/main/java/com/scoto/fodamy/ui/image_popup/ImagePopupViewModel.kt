package com.scoto.fodamy.ui.image_popup

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.scoto.fodamy.network.models.Image
import com.scoto.fodamy.network.models.ImageList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagePopupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    fun getImages(): ImageList {
        val images = savedStateHandle.get<ImageList>("images")
        val populatedImages = mutableListOf<Image>()
        if (images?.images?.size == 1) {
            val image = images.images[0]
            for (i in 0 until 4) {
                populatedImages.add(image)
            }
        }

        return ImageList(populatedImages)
    }
}
