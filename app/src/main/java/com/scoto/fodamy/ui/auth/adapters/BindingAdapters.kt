package com.scoto.fodamy.ui.auth.adapters

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {


    @JvmStatic
    @BindingAdapter("progressbarState")
    fun setProgressbarState(view: View, state: Boolean) {
        view.visibility = when (state) {
            true -> View.VISIBLE
            else -> View.GONE
        }
    }

}