package com.scoto.fodamy.ui.auth.adapters

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter


@BindingAdapter("progressbarState")
fun setProgressbarState(view: View, state: Boolean) {
    view.isVisible = state
}
