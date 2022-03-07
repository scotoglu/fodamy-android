package com.scoto.fodamy.ui.home

import androidx.lifecycle.viewModelScope
import com.scoto.domain.usecase.LogoutUseCase
import com.scoto.domain.usecase.params.NoParams
import com.scoto.domain.utils.DataStoreManager
import com.scoto.fodamy.helper.SingleLiveEvent
import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    val event: SingleLiveEvent<HomeViewEvent> = SingleLiveEvent()



     fun isLogin() = viewModelScope.launch {
        event.value = HomeViewEvent.IsLogin(dataStoreManager.isLogin())
    }

    fun logout() = viewModelScope.launch {
        if (dataStoreManager.isLogin()) {
            sendRequest(
                loading = true,
                request = { logoutUseCase.invoke(NoParams(Any())) },
                success = {
                    event.value = HomeViewEvent.Success
                    showMessage(it.message)
                }
            )
        }
    }
}
