package com.scoto.fodamy.ui.auth.signup

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentRegisterBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, RegisterViewModel>(R.layout.fragment_register)
