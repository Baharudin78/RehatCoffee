package com.rehat.rehatcoffee.domain.cart.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartDataEntity(
    val id: String? = null,
    val product: ProductCartEntity? = null,
    val qty: Int? = null,
    val totalPrice: Int? = null,
) : Parcelable
