package com.scoto.fodamy.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<V : ViewDataBinding>(
    @LayoutRes val layoutId: Int
) : Fragment() {

    protected lateinit var binding: V

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        binding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )

        return binding.root
    }

    protected fun navigateTo(directions: NavDirections) {
        navController.navigate(directions)
    }

    protected fun backTo() {
        navController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}
