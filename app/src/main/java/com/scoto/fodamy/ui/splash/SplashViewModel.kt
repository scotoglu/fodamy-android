package com.scoto.fodamy.ui.splash

import androidx.lifecycle.viewModelScope
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    fun splash(){
        viewModelScope.launch {
            if (dataStoreManager.isFirstTimeLaunch()) {
                toWalkthrough()
                saveFirstLaunch()
            } else {
                toHome()
            }
        }
    }
    private fun saveFirstLaunch() {
        viewModelScope.launch {
            dataStoreManager.saveFirstTimeLaunched()
        }
    }

    private fun toHome() {
        navigate(SplashFragmentDirections.actionSplashFragmentToBottomNavHome())
    }

    private fun toWalkthrough() {
        navigate(SplashFragmentDirections.actionSplashFragmentToWalkThroughFragment())
    }
}
