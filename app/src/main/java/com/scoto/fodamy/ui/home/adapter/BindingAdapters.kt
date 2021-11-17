package com.scoto.fodamy.ui.home.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scoto.fodamy.R


@BindingAdapter(value = ["app:recipeCount", "app:followerCount"])
fun setRecipeAndFollowers(tv: TextView, recipes: Int, followers: Int) {
    val recipeTxt = tv.context.resources.getString(R.string.recipe, recipes)
    val followingTxt = tv.context.resources.getString(R.string.follower, followers)
    tv.text = "$recipeTxt $followingTxt"
}

@BindingAdapter(value = ["app:commentCount", "app:likeCount"])
fun setCommentAndLike(tv: TextView, comments: Int, likes: Int) {
    val commentTxt = tv.context.resources.getString(R.string.comment, comments)
    val likeTxt = tv.context.resources.getString(R.string.like, likes)
    tv.text = "$commentTxt $likeTxt"

}

@BindingAdapter("imageLoaderCircle")
fun setImageWithGlideCircle(iv: ImageView, url: String) {
    Glide
        .with(iv.context)
        .load(url)
        .placeholder(R.drawable.ic_user_placeholder)
        .apply(RequestOptions.circleCropTransform())
        .into(iv)
}

@BindingAdapter("imageLoaderNormal")
fun setImageWithGlideNormal(iv: ImageView, url: String) {
    Glide
        .with(iv.context)
        .load(url)
        .placeholder(R.drawable.photo)
        .into(iv)
}

@BindingAdapter("badgeVisibility")
fun setBadgeVisibility(iv: ImageView, isVisible: Boolean) {
    iv.isVisible = isVisible
}