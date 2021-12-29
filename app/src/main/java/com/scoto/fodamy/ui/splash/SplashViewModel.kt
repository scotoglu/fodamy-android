package com.scoto.fodamy.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    val isFirstTime: LiveData<String>
        get() = dataStoreManager.isFirstTimeLaunch.asLiveData()

    fun saveFirstLaunch() {
        viewModelScope.launch {
            dataStoreManager.saveFirstTimeLaunched()
        }
    }

    fun toHome() {
        navigate(SplashFragmentDirections.actionSplashFragmentToBottomNavHome())
    }

    fun toWalkthrough() {
        navigate(SplashFragmentDirections.actionSplashFragmentToWalkThroughFragment())
    }
}
