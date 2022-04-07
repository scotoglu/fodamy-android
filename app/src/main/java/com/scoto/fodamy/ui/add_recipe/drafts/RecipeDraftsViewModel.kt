package com.scoto.fodamy.ui.add_recipe.drafts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.usecase.LogoutUseCase
import com.scoto.domain.usecase.params.NoParams
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 14.03.2022
 */
@HiltViewModel
class RecipeDraftsViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    val event = SingleLiveEvent<RecipeDraftsEvent>()

    private val _drafts: MutableLiveData<List<RecipeDraft>> = MutableLiveData()
    val drafts: LiveData<List<RecipeDraft>> get() = _drafts

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

    fun toChoosePhoto(draft: RecipeDraft) {
        navigate(
            RecipesDraftsFragmentDirections.actionRecipesDraftsFragmentToChoosePhotoFragment(
                draft
            )
        )
    }

    fun toAddRecipe() {
        navigate(
            RecipesDraftsFragmentDirections.actionRecipesDraftsFragmentToAddRecipeFragment(
                null,
                false
            )
        )
    }

    fun toEdit(draft: RecipeDraft) {
        navigate(
            RecipesDraftsFragmentDirections.actionRecipesDraftsFragmentToAddRecipeFragment(
                draft,
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

    fun isLogin() = viewModelScope.launch {
        event.value = RecipeDraftsEvent.IsLogin(dataStoreManager.isLogin())
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                loading = true,
                request = { logoutUseCase.invoke(NoParams(Any())) },
                success = {
                    event.value = RecipeDraftsEvent.Success
                    showMessage(it.message)
                }
            )
        }
    }
}
