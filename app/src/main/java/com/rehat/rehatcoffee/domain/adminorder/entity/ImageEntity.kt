package com.rehat.rehatcoffee.domain.adminorder.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageEntity(
    val name: String? = null,
    val publicId: String? = null,
    val url: String? = null
) : Parcelable
