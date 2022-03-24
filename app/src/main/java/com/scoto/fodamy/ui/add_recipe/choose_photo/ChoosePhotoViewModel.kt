package com.scoto.fodamy.ui.add_recipe.choose_photo

import android.os.Bundle
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 17.03.2022
 */
@HiltViewModel
class ChoosePhotoViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    val event = SingleLiveEvent<ChoosePhotoFragmentEvent>()
    private var draft: RecipeDraft? = null
    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        with(ChoosePhotoFragmentArgs.fromBundle(bundle)) {
            this@ChoosePhotoViewModel.draft = draft
        }
    }

    fun getSelectedImageIfHas(): List<File>? {
        return draft?.image
    }

    fun toPublishDraft(images: List<File>) {
        draft?.image = images
        sendRequest(
            loading = true,
            request = { draft?.let { recipeRepository.updateDraft(it) } },
            success = {
                if (draft != null) {
                    navigate(
                        ChoosePhotoFragmentDirections.actionChoosePhotoFragmentToPublishDraftFragment(
                            draft!!
                        )
                    )
                }
            }
        )
    }

    override fun backTo() {
        draft?.let {
            navigate(
                ChoosePhotoFragmentDirections.actionChoosePhotoFragmentToPublishDraftFragment(it)
            )
        }
    }

    fun openCamera() {
        event.value = ChoosePhotoFragmentEvent.OpenCamera
    }

    fun openGallery() {
        event.value = ChoosePhotoFragmentEvent.OpenGallery
    }
}
