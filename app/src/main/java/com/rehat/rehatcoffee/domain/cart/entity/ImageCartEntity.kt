package com.rehat.rehatcoffee.domain.cart.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageCartEntity(
    val name: String? = null,
    val publicId: String? = null,
    val url: String? = null
) : Parcelable
