package com.scoto.fodamy.ui.add_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.scoto.domain.models.NumberOfPerson
import com.scoto.domain.models.TimeOfRecipe
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.03.2022
 */
@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : BaseViewModel() {

    val event: SingleLiveEvent<AddRecipeEvent> = SingleLiveEvent()

    // Recipe Times ( ie. 2dk,5dk)
    private val _times: MutableLiveData<List<TimeOfRecipe>> = MutableLiveData()
    val times: LiveData<List<TimeOfRecipe>> get() = _times

    // Recipe Serving ( ie. 2-4 person, 4-6 person)
    private val _serving: MutableLiveData<List<NumberOfPerson>> = MutableLiveData()
    val serving: LiveData<List<NumberOfPerson>> get() = _serving

    init {
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

    fun sendRecipe() {}
}
