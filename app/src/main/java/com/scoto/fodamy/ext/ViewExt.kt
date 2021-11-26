package com.scoto.fodamy.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.onClick(it: (View) -> Unit) {
    this.setOnClickListener(it)
}

fun View.snackbar(message: String) {
    this.let {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }
}