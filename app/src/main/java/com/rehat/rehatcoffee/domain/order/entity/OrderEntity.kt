package com.rehat.rehatcoffee.domain.order.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderEntity(
    val id: String? = null,
    val orderStatus: Boolean? = null,
    val product: List<ProductOrderListEntity>,
    val totalPrice: String? = null,
) : Parcelable
