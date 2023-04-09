package com.rehat.rehatcoffee.domain.order.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductOrderListEntity(
    val id: String? = null,
    val product: ProductOrderDetailEntity? = null,
    val qty: Int? = null,
    val totalPrice: Int? = null,
) : Parcelable
