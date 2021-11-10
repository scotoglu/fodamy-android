package com.scoto.fodamy.ext

import com.google.gson.Gson
import com.scoto.fodamy.ui.auth.login.ErrorResponse
import okhttp3.ResponseBody


fun ResponseBody.parseResponse(): String {
    val message = Gson().fromJson(this.charStream(), ErrorResponse::class.java)
    return message.error
}