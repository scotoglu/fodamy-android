package com.scoto.fodamy.ext

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.snackbar.Snackbar
import com.scoto.fodamy.R

fun AppCompatActivity.hideSystemUI(container: View) {

    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, container).hide(
        WindowInsetsCompat.Type.statusBars()
            or WindowInsetsCompat.Type.navigationBars()
    )
}

fun AppCompatActivity.showSystemUI(container: View) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(
        window,
        container
    ).show(
        WindowInsetsCompat.Type.statusBars()
            or WindowInsetsCompat.Type.navigationBars()
    )
}

fun AppCompatActivity.hideIme() {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller?.hide(WindowInsetsCompat.Type.ime())
}

fun AppCompatActivity.showIme() {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller?.show(WindowInsetsCompat.Type.ime())
}

fun AppCompatActivity.showMessage(@StringRes message: Int) {
    val snackbar = Snackbar.make(findViewById(android.R.id.content), getString(message), Snackbar.LENGTH_LONG)
    snackbar.setAction(R.string.text_action) { snackbar.dismiss() }
    snackbar.show()
}
