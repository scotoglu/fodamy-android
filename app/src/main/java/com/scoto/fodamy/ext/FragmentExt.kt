package com.scoto.fodamy.ext

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.scoto.fodamy.R

/**
 * @author Sefa ÇOTOĞLU
 * Created 29.12.2021 at 11:19
 */

fun Fragment.snackbar(message: String, anchorView: View?) {
    this.let {
        val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
        snackbar.anchorView = anchorView
        snackbar.setAction(R.string.text_action) { snackbar.dismiss() }
        // align to top of screen
//        val view = snackbar.view
//        val params: FrameLayout.LayoutParams = view.layoutParams as FrameLayout.LayoutParams
//        params.gravity = Gravity.TOP
//        view.layoutParams = params
        snackbar.show()
    }
}
