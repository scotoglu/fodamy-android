package com.scoto.data.local.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * @author Sefa ÇOTOĞLU
 * Created 15.02.2022 at 14:05
 */
inline fun <reified T> type(): Type = object : TypeToken<T>() {}.type

inline fun <reified T> toJson(src: Any): String {
    return Gson().toJson(src, type<T>())
}

inline fun <reified T> fromJson(src: String): T {
    return Gson().fromJson(src, type<T>())
}
