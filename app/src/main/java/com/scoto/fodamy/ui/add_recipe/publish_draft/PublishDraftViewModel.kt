package com.scoto.fodamy.ui.add_recipe.publish_draft

import android.os.Bundle
import com.scoto.domain.models.RecipeDraft
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.03.2022
 */
@HiltViewModel
class PublishDraftViewModel @Inject constructor() : BaseViewModel() {

    var draft: RecipeDraft? = null
    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        with(PublishDraftFragmentArgs.fromBundle(bundle)) {
            draft = recipeDraft
        }
    }

    fun toEdit() {
        navigate(
            PublishDraftFragmentDirections.actionPublishDraftFragmentToAddRecipeFragment(
                draft,
                true
            )
        )
    }

    fun publishDraft() {
    }
}
