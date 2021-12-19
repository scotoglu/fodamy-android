package com.scoto.fodamy.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.scoto.fodamy.helper.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val isFirstTime: LiveData<String>
        get() = dataStoreManager.isFirstTimeLaunch.asLiveData()

    fun saveFirstLaunch() {
        viewModelScope.launch {
            dataStoreManager.saveFirstTimeLaunched()
        }
    }
}
