package com.scoto.fodamy.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("first_item")
    val firstItem: Int,
    @SerializedName("last_item")
    val lastItem: Int,
    @SerializedName("last_page")
    val lastPage: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int
) : Parcelable
