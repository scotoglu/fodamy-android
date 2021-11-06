package com.scoto.fodamy.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()




    init {

    }

    fun doLoginRequest() {


    }

    private fun validateInputs(): Boolean {
        val isValid = false

        return isValid
    }



}
