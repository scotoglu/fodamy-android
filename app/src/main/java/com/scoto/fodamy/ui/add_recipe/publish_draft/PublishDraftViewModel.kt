package com.scoto.fodamy.ui.add_recipe.publish_draft

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.03.2022
 */
@HiltViewModel
class PublishDraftViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    private val _recipeDraft: MutableLiveData<RecipeDraft> = MutableLiveData()
    val recipeDraft: LiveData<RecipeDraft> get() = _recipeDraft

    var draft: RecipeDraft? = null
    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        with(PublishDraftFragmentArgs.fromBundle(bundle)) {
            draft = recipeDraft
            this@PublishDraftViewModel._recipeDraft.value = recipeDraft
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

    fun toChoosePhoto() {
        navigate(
            PublishDraftFragmentDirections.actionPublishDraftFragmentToChoosePhotoFragment(
                draft
            )
        )
    }

    fun publish() {
    }
}
