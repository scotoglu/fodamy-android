package com.scoto.fodamy.ui.add_recipe.publish_draft

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.03.2022
 */
@HiltViewModel
class PublishDraftViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    private val _recipeDraft: MutableLiveData<RecipeDraft> = MutableLiveData()
    val recipeDraft: LiveData<RecipeDraft> get() = _recipeDraft

    // checkbox for user agreement
    val isChecked = MutableLiveData<Boolean>(false)

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

    fun publish() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            if (isChecked.value == true) {
                sendRequest(
                    loading = true,
                    request = {
                        draft?.let {
                            recipeRepository.sendRecipe(
                                title = it.title,
                                ingredients = it.ingredients,
                                directions = it.direction,
                                timeOfRecipeId = it.timeOfRecipe.id,
                                numberOfPersonId = it.numberOfPerson.id,
                                categoryId = it.category.id,
                                images = it.image
                            )
                        }
                    },
                    success = {
                        showMessageWithRes(R.string.success_send_recipe)
                        deleteDraftAfterPublish()
                    }
                )
            } else {
                showMessageWithRes(R.string.accept_terms)
            }
        } else {
            openNavigationDilog(R.id.action_global_authDialog)
        }
    }

    private fun deleteDraftAfterPublish() {
        sendRequest(
            request = {
                draft?.id?.let { id -> recipeRepository.deleteDraft(id) }
            },
            success = { backTo() }
        )
    }
}
