package com.rehat.rehatcoffee.data.cart.remote.dto

import com.google.gson.annotations.SerializedName

data class CartDataResponse(
    @SerializedName("_id")val id: String? = null,
    @SerializedName("product")val product: ProductCartResponse?,
    @SerializedName("qty")val qty: Int? = null,
    @SerializedName("total_price")val totalPrice: Int? = null,
)