package com.rehat.rehatcoffee.domain.menu.entity

import android.os.Parcelable
import com.rehat.rehatcoffee.data.menu.remote.dto.ImageMenuResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuEntity(
    val id: String? = null,
    val description: String? = null,
    val imagesEntity: List<ImageMenuEntity?>,
    val price: Int? = null,
    val productName: String? = null,
) : Parcelable
