package com.scoto.fodamy.ext

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

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
