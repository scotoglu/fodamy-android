package com.scoto.fodamy.ext

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Patterns


fun String.spannableText(text: String = ""): SpannableString {
    val start = text.length
    val end = if (text.isEmpty()) this.length else text.length + this.length

    val spannable = SpannableString(text + this)
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        start,
        end,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable

}

fun String.isEmailValid(): Boolean {
    if (this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
        return true
    }
    return false
}