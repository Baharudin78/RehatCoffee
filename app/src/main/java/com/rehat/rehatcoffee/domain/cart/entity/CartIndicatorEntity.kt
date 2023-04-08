package com.rehat.rehatcoffee.domain.cart.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartIndicatorEntity(
    val totalCart : Int? = null
) : Parcelable
