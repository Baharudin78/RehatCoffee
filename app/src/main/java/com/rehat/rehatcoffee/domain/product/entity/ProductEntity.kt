package com.rehat.rehatcoffee.domain.product.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductEntity(
    val description: String? = null,
    val id: String? = null,
    val images: List<ImageProductEntity?>,
    val kinds: String? = null,
    val price: Int? = null,
    val productName: String? = null,
) : Parcelable
