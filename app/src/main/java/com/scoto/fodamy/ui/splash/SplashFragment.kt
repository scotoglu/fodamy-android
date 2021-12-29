package com.scoto.fodamy.ui.splash

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentSplashBinding
import com.scoto.fodamy.ui.base.BaseFragment_V2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment_V2<FragmentSplashBinding, SplashViewModel>(
    R.layout.fragment_splash
) {

    override fun registerObservables() {
        super.registerObservables()
        viewModel.isFirstTime.observe(viewLifecycleOwner, { isFirstTime ->
            if (isFirstTime.isBlank()) {
                viewModel.toWalkthrough()
                viewModel.saveFirstLaunch()
            } else {
                viewModel.toHome()
            }
        })
    }
}
