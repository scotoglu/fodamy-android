package com.scoto.fodamy.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.scoto.fodamy.network.models.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val language: String,
    @SerializedName("main_category_id")
    val mainCategoryId: Int,
    val image: Image,

) : Parcelable
