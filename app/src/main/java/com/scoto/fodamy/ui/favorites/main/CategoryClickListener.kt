package com.scoto.fodamy.ui.favorites.main

import com.scoto.fodamy.network.models.Category

interface CategoryClickListener {
    fun onItemClicked(item: Category)
}