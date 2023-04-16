package com.rehat.rehatcoffee.domain.adminorder.entity

import android.os.Parcelable
import com.rehat.rehatcoffee.data.adminorder.remote.dto.ImageProductResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminProductItemOrderEntity(
    val id: String? = null,
    val images: List<ImageEntity?>,
    val kinds: String? = null,
    val price: Int? = null,
    val productName: String? = null
) : Parcelable
