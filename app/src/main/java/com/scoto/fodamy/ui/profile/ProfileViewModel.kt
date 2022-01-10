package com.scoto.fodamy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.Recipe
import com.scoto.fodamy.network.models.User
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.network.repositories.RecipeRepository
import com.scoto.fodamy.network.repositories.UserRepository
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 13.12.2021 at 14:16
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    private val _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: LiveData<PagingData<Recipe>> get() = _recipes

    val event: SingleLiveEvent<ProfileEvent> = SingleLiveEvent()

    init {
        getUserDetails()
        isLogin()
    }

    fun getUserDetails() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = userRepository.getUserDetails(dataStoreManager.getUserId())) {
                is NetworkResponse.Error -> {
                    showMessage(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    response.data.let {
                        _user.value = it
                    }
                    getSomeData()
                }
            }
        } else {
            showMessageWithRes(R.string.required_auth)
        }
    }

    fun isLogin() = viewModelScope.launch {
        event.value = ProfileEvent.IsLogin(dataStoreManager.isLogin())
    }

    fun logout() = viewModelScope.launch {
        when (val response = authRepository.logout()) {
            is NetworkResponse.Success -> {
                event.value = ProfileEvent.Success(response.data.message)
            }
            is NetworkResponse.Error -> {
                showMessage(response.exception.handleException())
            }
        }
    }

    suspend fun isLoginLiveData(): LiveData<String> = dataStoreManager.isLoginLiveData()

    fun onLogin() {
        navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFlow())
    }

    private fun getSomeData() = viewModelScope.launch {
        recipeRepository.getLastAdded().cachedIn(viewModelScope).collect { pagingData ->
            _recipes.value = pagingData
        }
    }
}
