package com.rehat.rehatcoffee.data.cart.remote.dto

import com.google.gson.annotations.SerializedName

data class GetCartResponse(
    @SerializedName("cart_data")val cartData: List<CartDataResponse?>,
    @SerializedName("total_price")val totalPrice: Int? = null
)