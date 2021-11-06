package com.scoto.fodamy.ui.walkthrough

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentWalkThroughBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalkThroughFragment : BaseFragment<FragmentWalkThroughBinding, WalkThroughViewModel>(
    R.layout.fragment_walk_through
) {

    override fun getViewModel(): Class<WalkThroughViewModel> = WalkThroughViewModel::class.java


}