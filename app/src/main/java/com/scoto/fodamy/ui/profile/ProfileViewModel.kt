package com.scoto.fodamy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.scoto.domain.models.Recipe
import com.scoto.domain.models.User
import com.scoto.domain.repositories.RecipeRepository
import com.scoto.domain.repositories.UserRepository
import com.scoto.domain.usecase.LogoutUseCase
import com.scoto.domain.usecase.params.NoParams
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.R
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import com.scoto.fodamy.util.FROM_LAST_ADDED
import com.scoto.fodamy.util.paging_sources.RecipePagingSource
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
    private val recipeRepository: RecipeRepository,
    private val dataStoreManager: DataStoreManager,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    private val _recipes: MutableLiveData<PagingData<Recipe>> = MutableLiveData()
    val recipes: LiveData<PagingData<Recipe>> get() = _recipes

    val event: SingleLiveEvent<ProfileEvent> = SingleLiveEvent()

    init {
        getUserDetails()
    }

    fun getUserDetails() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                request = { userRepository.getUserDetails(dataStoreManager.getUserId()) },
                success = {
                    _user.value = it
                    getSomeData()
                }
            )
        } else {
            showMessageWithRes(R.string.required_auth)
        }
    }

    fun isLogin() = viewModelScope.launch {
        event.value = ProfileEvent.IsLogin(dataStoreManager.isLogin())
    }

    fun logout() {
        sendRequest(
            loading = true,
            request = { logoutUseCase.invoke(NoParams(Any())) },
            success = {
                event.value = ProfileEvent.Success
                showMessage(it.message)
            }
        )
    }

    suspend fun isLoginLiveData(): LiveData<String> = dataStoreManager.isLoginLiveData()

    fun toLogin() {
        navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFlow())
    }

    private fun getSomeData() {
        sendRequest(
            request = {
                Pager(
                    config = pageConfig,
                    pagingSourceFactory = {
                        RecipePagingSource(
                            recipeRepository,
                            FROM_LAST_ADDED,
                            null
                        )
                    }
                ).flow
            },
            success = {
                viewModelScope.launch {
                    it.cachedIn(viewModelScope).collect {
                        _recipes.value = it
                    }
                }
            }
        )
    }

    companion object {
        private val pageConfig = PagingConfig(
            pageSize = 24,
            maxSize = 100,
            enablePlaceholders = false
        )
    }
}
