package com.scoto.fodamy.ui.home.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.loadCircleImageFromUrl
import com.scoto.fodamy.ext.loadImageFromUrl
import com.scoto.fodamy.util.CustomToolbar

@BindingAdapter(value = ["app:recipeCount", "app:followerCount"])
fun setRecipeAndFollowers(tv: TextView, recipes: Int?, followers: Int?) {
    tv.text = tv.context.resources.getString(
        R.string.recipes_and_following_count,
        recipes, followers
    )
}
@BindingAdapter(value = ["app:followingCount", "app:followerCount"])
fun setFollwingAndFollowers(tv: TextView, following: Int?, followers: Int?) {
    tv.text = tv.context.resources.getString(
        R.string.recipes_and_following_count,
        following, followers
    )
}
@BindingAdapter(value = ["app:commentCount", "app:likeCount"])
fun setCommentAndLike(tv: TextView, comments: Int?, likes: Int?) {
    tv.text = tv.context.resources.getString(
        R.string.comment_and_like_count,
        comments, likes
    )
}

@BindingAdapter("imageLoaderCircle")
fun setImageWithGlideCircle(iv: ImageView, url: String?) {
    iv.loadCircleImageFromUrl(url)
}

@BindingAdapter("imageLoaderNormal")
fun setImageWithGlideNormal(iv: ImageView, url: String?) {
    iv.loadImageFromUrl(url)
}

@BindingAdapter("app:onBackListener")
fun setOnBackListener(toolbar: CustomToolbar, listener: CustomToolbar.OnBackListener) {
    toolbar.onBackListener = listener
}

@BindingAdapter("app:onEndIconListener")
fun setOnEndIconListener(toolbar: CustomToolbar, listener: CustomToolbar.OnEndIconListener) {
    toolbar.onEndIconListener = listener
}
