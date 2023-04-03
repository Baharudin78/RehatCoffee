package com.rehat.rehatcoffee.domain.menu.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageMenuEntity(
    val name: String? = null,
    val publicId: String? = null,
    val url: String? = null
) : Parcelable
