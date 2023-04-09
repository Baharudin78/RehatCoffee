package com.rehat.rehatcoffee.domain.order.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageProductOrderEntity(
    val name: String? = null,
    val publicId: String? = null,
    val url: String? = null
) : Parcelable
