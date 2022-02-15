package com.scoto.data.local.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.02.2022 at 14:05
 */
fun <T> type(): Type = object : TypeToken<T>() {}.type

fun <T> toJson(src: Any): String {
    return Gson().toJson(src, type<T>())
}

fun <T> fromJson(src: String): T {
    return Gson().fromJson(src, type<T>())
}
