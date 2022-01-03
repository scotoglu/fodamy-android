package com.scoto.fodamy.ui.home.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.scoto.fodamy.R
import com.scoto.fodamy.ext.loadCircleImageFromUrl
import com.scoto.fodamy.ext.loadImageFromUrl
import com.scoto.fodamy.ext.spannableNum
import com.scoto.fodamy.ext.spannableText
import com.scoto.fodamy.helper.states.InputErrorType
import com.scoto.fodamy.util.CustomStateView
import com.scoto.fodamy.util.CustomToolbar
import com.scoto.fodamy.util.SpannableType


@BindingAdapter("app:fieldVisibility")
fun setFieldVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("app:warningFieldText")
fun setWarningFieldText(view: TextView, event: InputErrorType?) {
    when (event) {
        InputErrorType.Email -> view.text = view.context.getString(R.string.required_field_email)
        InputErrorType.Password -> view.text =
            view.context.getString(R.string.required_field_password_length)
        InputErrorType.Username -> view.text =
            view.context.getString(R.string.required_field_username)
        else -> {}
    }
}

@BindingAdapter("app:spannableText")
fun setSpannableText(view: TextView, type: SpannableType) {
    when (type) {
        SpannableType.REGISTER -> {
            view.text = view.context.getString(R.string.have_account)
                .spannableText(view.context.getString(R.string.login))
        }
        SpannableType.LOGIN -> {
            view.text = view.context.getString(R.string.dont_have_account)
                .spannableText(view.context.getString(R.string.signup))
        }
    }
}

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

@BindingAdapter("app:onRetryClick")
fun setOnRetryClick(stateView: CustomStateView, listener: CustomStateView.OnRetryClick) {
    stateView.onRetryClick = listener
}

@BindingAdapter(value = ["comments", "likes"])
fun setCommentAndLikeCount(tv: TextView, comments: Int?, likes: Int?) {
    tv.text = tv.context.resources.getString(
        R.string.comment_and_like_count,
        comments ?: 0,
        likes ?: 0
    )
}

@BindingAdapter("app:comments")
fun setCommentsCount(tv: TextView, comments: Int?) {
    val commentTxt = tv.context.resources.getString(R.string.comment, comments)
    tv.text = commentTxt.spannableNum(0, comments.toString().length)
}

@BindingAdapter("app:likes")
fun setLikes(tv: TextView, likes: Int?) {
    likes?.let {
        val likesTxt = tv.context.resources.getString(R.string.like, likes)
        tv.text = likesTxt.spannableNum(0, likes.toString().length)
    }
}

@BindingAdapter("app:timeOfRecipe")
fun setTimeOfRecipe(tv: TextView, timeOfRecipe: String?) {
    timeOfRecipe?.let {
        tv.text = tv.context.resources.getString(R.string.timeOfRecie, timeOfRecipe)
    }
}

@BindingAdapter("progressbarState")
fun setProgressbarState(view: View, state: Boolean) {
    view.isVisible = state
}
