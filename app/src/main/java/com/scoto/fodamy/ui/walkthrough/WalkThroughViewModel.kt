package com.scoto.fodamy.ui.walkthrough

import com.scoto.fodamy.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalkThroughViewModel @Inject constructor() : BaseViewModel() {

    fun toHome() {
        navigate(WalkThroughFragmentDirections.actionWalkThroughFragmentToBottomNavHome())
    }
}
