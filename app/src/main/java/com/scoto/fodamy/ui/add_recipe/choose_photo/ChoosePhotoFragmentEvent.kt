package com.scoto.fodamy.ui.add_recipe.choose_photo

/**
 * @author Sefa ÇOTOĞLU
 * Created 17.03.2022
 */
sealed class ChoosePhotoFragmentEvent {
    object OpenCamera : ChoosePhotoFragmentEvent()
    object OpenGallery : ChoosePhotoFragmentEvent()
}
