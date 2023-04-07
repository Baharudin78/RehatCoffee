package com.rehat.rehatcoffee.domain.cart.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCartEntity(
    val cartData: List<CartDataEntity?>,
    val totalPrice: Int
) : Parcelable
