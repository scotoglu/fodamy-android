package com.scoto.fodamy.ui.splash

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentSplashBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    R.layout.fragment_splash
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isFirstTime.observe(viewLifecycleOwner, { isFirstTime ->
            val navController = findNavController()
            if (isFirstTime.isNullOrEmpty()) {
                navController.navigate(SplashFragmentDirections.actionSplashFragmentToWalkThroughFragment())
                viewModel.saveFirstLaunch()
            } else {
                navController.navigate(SplashFragmentDirections.actionSplashFragmentToNavMain())
            }
        })
    }


    override fun getViewModelClass(): Class<SplashViewModel> = SplashViewModel::class.java


}

