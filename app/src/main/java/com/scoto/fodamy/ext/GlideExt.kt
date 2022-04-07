package com.scoto.fodamy.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scoto.fodamy.R

fun ImageView.loadImageFromUrl(url: String?) {
    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.photo)
        .into(this)
}

fun ImageView.loadCircleImageFromUrl(url: String?) {
    Glide
        .with(this)
        .load(url)
        .placeholder(R.drawable.ic_user_placeholder)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}
