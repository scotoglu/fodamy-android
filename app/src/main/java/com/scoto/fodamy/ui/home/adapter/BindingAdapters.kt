package com.scoto.fodamy.ui.home.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.loadCircleImageFromUrl
import com.scoto.fodamy.ext.loadImageFromUrl
import com.scoto.fodamy.util.CustomToolbar


@BindingAdapter(value = ["app:recipeCount", "app:followerCount"])
fun setRecipeAndFollowers(tv: TextView, recipes: Int?, followers: Int?) {
    val recipeTxt = tv.context.resources.getString(R.string.recipe, recipes)
    val followingTxt = tv.context.resources.getString(R.string.follower, followers)
    tv.text = "$recipeTxt $followingTxt"
}

@BindingAdapter(value = ["app:commentCount", "app:likeCount"])
fun setCommentAndLike(tv: TextView, comments: Int?, likes: Int?) {
    val commentTxt = tv.context.resources.getString(R.string.comment, comments)
    val likeTxt = tv.context.resources.getString(R.string.like, likes)
    tv.text = "$commentTxt $likeTxt"

}

@BindingAdapter("imageLoaderCircle")
fun setImageWithGlideCircle(iv: ImageView, url: String?) {
    if (url != null) iv.loadCircleImageFromUrl(url)

}

@BindingAdapter("imageLoaderNormal")
fun setImageWithGlideNormal(iv: ImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        iv.loadImageFromUrl(url)
    }
}

@BindingAdapter("badgeVisibility")
fun setBadgeVisibility(iv: ImageView, isVisible: Boolean) {
    iv.isVisible = isVisible
}


@BindingAdapter("app:onBackListener")
fun setOnBackListener(toolbar: CustomToolbar, listener: CustomToolbar.OnBackListener) {
    toolbar.onBackListener = listener
}

@BindingAdapter("app:onEndIconListener")
fun setOnEndIconListener(toolbar: CustomToolbar, listener: CustomToolbar.OnEndIconListener) {
    toolbar.onEndIconListener = listener
}
