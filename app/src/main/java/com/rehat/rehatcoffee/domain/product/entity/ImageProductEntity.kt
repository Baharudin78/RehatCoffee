package com.rehat.rehatcoffee.domain.product.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageProductEntity(
    val name: String? = null,
    val publicId: String? = null,
    val url: String? = null
) : Parcelable
