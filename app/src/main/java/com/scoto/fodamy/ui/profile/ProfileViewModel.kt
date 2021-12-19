package com.scoto.fodamy.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.handleException
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.helper.states.NetworkResponse
import com.scoto.fodamy.network.models.User
import com.scoto.fodamy.network.repositories.AuthRepository
import com.scoto.fodamy.network.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: MutableLiveData<User> get() = _user
    val event: SingleLiveEvent<UIProfileEvent> = SingleLiveEvent()

    init {
        getUserDetails()
    }

    private fun getUserDetails() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = userRepository.getUserDetails(dataStoreManager.getUserId())) {
                is NetworkResponse.Error -> {
                    event.value = UIProfileEvent.ShowMessage(response.exception.handleException())
                }
                is NetworkResponse.Success -> {
                    response.data.let {
                        _user.value = it
                    }
                }
            }
        } else {

            event.value = UIProfileEvent.ShowMessage("Giriş Yapmalısınız...")
        }
    }

    suspend fun isLoginLiveData(): LiveData<String> =
        dataStoreManager.isLoginLiveData()

    fun onLogoutClick() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            when (val response = authRepository.logout()) {
                is NetworkResponse.Success -> {
                    event.value = UIProfileEvent.ShowMessage(response.data.message)
                }
                is NetworkResponse.Error -> {
                    event.value =
                        UIProfileEvent.ShowMessage(response.exception.handleException())
                }
            }
        } else {
            event.value = UIProfileEvent.NavigateTo(R.id.action_global_authDialog)
        }
    }
}
