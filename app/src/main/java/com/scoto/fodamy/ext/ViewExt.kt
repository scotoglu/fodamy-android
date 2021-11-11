package com.scoto.fodamy.ext

import android.view.View

fun View.onClick(it: (View) -> Unit) {
    this.setOnClickListener(it)
}