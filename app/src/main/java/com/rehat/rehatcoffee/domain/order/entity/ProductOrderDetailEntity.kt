package com.rehat.rehatcoffee.domain.order.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductOrderDetailEntity(
    val id: String? = null,
    val images: List<ImageProductOrderEntity?>,
    val kinds: String? = null,
    val price: Int? = null,
    val productName: String? = null
) : Parcelable
