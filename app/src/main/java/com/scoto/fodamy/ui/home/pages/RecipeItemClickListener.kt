package com.scoto.fodamy.ui.home.pages

import com.scoto.fodamy.network.models.Recipe

interface RecipeItemClickListener {
    fun onItemClicked(recipe: Recipe)
}