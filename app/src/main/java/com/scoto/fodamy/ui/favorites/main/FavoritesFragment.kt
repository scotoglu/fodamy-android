package com.scoto.fodamy.ui.favorites.main

import com.scoto.fodamy.R
import com.scoto.fodamy.databinding.FragmentFavoritesBinding
import com.scoto.fodamy.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(R.layout.fragment_favorites) {
    override fun getViewModel(): Class<FavoritesViewModel> = FavoritesViewModel::class.java
}