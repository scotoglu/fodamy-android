package com.scoto.fodamy.ui.favorites.adapter

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.scoto.fodamy.R

@BindingAdapter("app:comments")
fun setCommentsCount(tv: TextView, comments: Int?) {
    val commentTxt = tv.context.resources.getString(R.string.comment, comments)
    tv.text = commentTxt
}

@BindingAdapter("app:likes")
fun setLikes(tv: TextView, likes: Int?) {
    val likesTxt = tv.context.resources.getString(R.string.like, likes)
    tv.text = likesTxt
}

@BindingAdapter("app:emptyComment")
fun setVisibilityEmptyComment(view: View, visibility: Boolean) {
    view.isVisible = visibility
}

@BindingAdapter("app:timeOfRecipe")
fun setTimeOfRecipe(tv: TextView, timeOfRecipe: String) {
    tv.text = tv.context.resources.getString(R.string.timeOfRecie, timeOfRecipe)
}