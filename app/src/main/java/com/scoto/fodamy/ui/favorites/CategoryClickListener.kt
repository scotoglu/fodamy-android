package com.scoto.fodamy.ui.favorites

import com.scoto.fodamy.network.models.Category

interface CategoryClickListener {
    fun onItemClicked(item: Category)
}