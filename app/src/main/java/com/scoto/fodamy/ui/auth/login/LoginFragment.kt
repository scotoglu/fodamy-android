package com.scoto.fodamy.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentLoginBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment:BaseFragment<FragmentLoginBinding,LoginViewModel>(
    R.layout.fragment_login
){
    override fun getViewModel(): Class<LoginViewModel> =LoginViewModel::class.java
}