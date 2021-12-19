package com.scoto.fodamy.ext

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.scoto.fodamy.R

fun View.onClick(it: (View) -> Unit) {
    this.setOnClickListener(it)
}

fun View.snackbar(message: String) {
    this.let {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
    }
}

fun TabLayout.addVerticalLineToTabs() {
    val root: View? = this.getChildAt(0)
    val drawable = GradientDrawable()
    drawable.apply {
        setColor(
            ContextCompat.getColor(
                this@addVerticalLineToTabs.context, R.color.zircon
            )
        )
        setSize(4, 1)
    }
    if (root is LinearLayout) {
        root.apply {
            showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            dividerDrawable = drawable
            dividerPadding = 10
        }
    }
}
