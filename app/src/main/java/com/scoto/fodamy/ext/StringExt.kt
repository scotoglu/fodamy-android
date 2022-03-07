package com.scoto.fodamy.ext

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

fun String.spannableText(text: String): SpannableString {
    val start = this.length
    val end = start + text.length

    val spannable = SpannableString(this + text)
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        start,
        end,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}

fun String.spannableNum(start: Int, end: Int): SpannableString {
    val spannable = SpannableString(this)
    spannable.setSpan(
        ForegroundColorSpan(Color.RED),
        start,
        end,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannable
}
