package com.rehat.rehatcoffee.domain.cart.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCartEntity(
    val id: String? = null,
    val images: List<ImageCartEntity?>,
    val kinds: String? = null,
    val price: Int? = null,
    val productName: String? = null
) : Parcelable
