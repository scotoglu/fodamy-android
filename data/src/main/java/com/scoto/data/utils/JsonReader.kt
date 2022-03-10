package com.scoto.data.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

/**
 * @author Sefa ÇOTOĞLU
 * Created 8.03.2022 at 16:04
 */
class JsonReader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun readJsonFromFile(fileName: String): String {
        val json: String
        return try {
            json = context.assets.open(fileName.trim()).bufferedReader().use {
                it.readText()
            }
            json
        } catch (ex: IOException) {
            throw ex
        }
    }
}
