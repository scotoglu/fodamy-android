package com.scoto.fodamy.ui.add_recipe.new_recipe

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scoto.domain.models.CategoryDraft
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.RecipeDraft
import com.scoto.domain.models.TimeOfRecipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.R
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.03.2022
 */
@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    // Recipe Times ( ie. 2 dk,5 dk)
    private val _times: MutableLiveData<List<TimeOfRecipe>> = MutableLiveData()
    val times: LiveData<List<TimeOfRecipe>> get() = _times

    // Recipe Serving ( ie. 2-4 kişi, 4-6 kişi)
    private val _serving: MutableLiveData<List<NumberOfPerson>> = MutableLiveData()
    val serving: LiveData<List<NumberOfPerson>> get() = _serving

    // Categories
    private val _categories: MutableLiveData<List<CategoryDraft>> = MutableLiveData()
    val categories: LiveData<List<CategoryDraft>> get() = _categories

    private var editMode = false
    var draft: RecipeDraft? = null

    val recipeTitle = MutableLiveData<String>()
    val recipeIngredients = MutableLiveData<String>()
    val recipeDirection = MutableLiveData<String>()
    var timeOfRecipe: TimeOfRecipe? = null
    var numberOfPerson: NumberOfPerson? = null
    var category: CategoryDraft? = null

    init {
        getRecipeTimes()
        getRecipeServing()
        getCategories()
    }

    override fun fetchExtras(bundle: Bundle) {
        super.fetchExtras(bundle)
        with(AddRecipeFragmentArgs.fromBundle(bundle)) {
            this@AddRecipeViewModel.editMode = editMode
            if (editMode) {
                this@AddRecipeViewModel.draft = editableDraft
                initialValues()
            }
        }
    }

    // if editMode == true, sets data that will be edited.
    private fun initialValues() {
        draft?.let { recipeDraft ->
            recipeTitle.value = recipeDraft.title
            recipeIngredients.value = recipeDraft.ingredients
            recipeDirection.value = recipeDraft.direction
            timeOfRecipe = recipeDraft.timeOfRecipe
            numberOfPerson = recipeDraft.numberOfPerson
            category = recipeDraft.category
        }
    }

    private fun getRecipeTimes() {
        sendRequest(
            loading = true,
            request = { recipeRepository.getRecipeTimes() },
            success = {
                _times.value = it
            }
        )
    }

    private fun getRecipeServing() {
        sendRequest(
            loading = true,
            request = { recipeRepository.getRecipeServing() },
            success = {
                _serving.value = it
            }
        )
    }

    private fun getCategories() {
        sendRequest(
            loading = true,
            request = { recipeRepository.getAllCategories() },
            success = {
                _categories.value = it
            }
        )
    }

    private fun updateDraft(draft: RecipeDraft) {
        sendRequest(
            loading = true,
            request = { recipeRepository.updateDraft(draft) },
            success = {
                navigate(
                    AddRecipeFragmentDirections.actionAddRecipeFragmentToPublishDraftFragment(
                        draft
                    )
                )
            }
        )
    }

    private fun checkFields(): Boolean {
        return when {
            recipeTitle.value.toString().isBlank() -> false
            recipeDirection.value.toString().isBlank() -> false
            recipeIngredients.value.toString().isBlank() -> false
            category == null -> false
            else -> true
        }
    }

    fun saveDraft() {
        if (!checkFields()) {
            showMessageWithRes(R.string.add_recipe_required_fields)
        } else {
            val draft = RecipeDraft(
                id = if (editMode) {
                    draft?.id!!
                } else {
                    UUID.randomUUID().toString()
                },
                title = recipeTitle.value.toString(),
                ingredients = recipeIngredients.value.toString(),
                direction = recipeDirection.value.toString(),
                category = category ?: CategoryDraft.EMPTY,
                numberOfPerson = numberOfPerson ?: NumberOfPerson.EMPTY,
                timeOfRecipe = timeOfRecipe ?: TimeOfRecipe.EMPTY,
                image = emptyList()
            )
            if (editMode) {
                updateDraft(draft)
            } else {
                sendRequest(
                    loading = true,
                    request = { recipeRepository.insertDraft(draft) },
                    success = {
                        navigate(
                            AddRecipeFragmentDirections.actionAddRecipeFragmentToPublishDraftFragment(
                                draft
                            )
                        )
                    }
                )
            }
        }
    }
}
