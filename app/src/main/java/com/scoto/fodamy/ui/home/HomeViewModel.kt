package com.scoto.fodamy.ui.home

import androidx.lifecycle.ViewModel
import com.scoto.fodamy.helper.DataStoreManager
import com.scoto.fodamy.network.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {


    companion object {
        private const val TAG = "HomeViewModel"
    }
}
