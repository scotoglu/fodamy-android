package com.scoto.fodamy.ui.add_recipe.drafts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
@HiltViewModel
class RecipeDraftsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    private val _drafts: MutableLiveData<List<RecipeDraft>> = MutableLiveData()
    val drafts: LiveData<List<RecipeDraft>> get() = _drafts

    var selectedDraft: RecipeDraft? = null

    fun getDrafts() {
        sendRequest(
            loading = true,
            request = { recipeRepository.getAllDrafts() },
            success = {
                _drafts.value = it
            }
        )
    }

    fun deleteDraft(draftId: String) {
        sendRequest(
            loading = true,
            request = { recipeRepository.deleteDraft(draftId) },
            success = { getDrafts() }
        )
    }

    fun toAddRecipe() {
        navigate(
            RecipesDraftsFragmentDirections.actionRecipesDraftsFragmentToAddRecipeFragment(
                selectedDraft,
                false
            )
        )
    }

    fun toEdit() {
        navigate(
            RecipesDraftsFragmentDirections.actionRecipesDraftsFragmentToAddRecipeFragment(
                selectedDraft,
                true
            )
        )
    }

    fun toPublishDraft(draft: RecipeDraft) {
        navigate(
            RecipesDraftsFragmentDirections.actionRecipesDraftsFragmentToPublishDraftFragment(
                draft
            )
        )
    }
}
