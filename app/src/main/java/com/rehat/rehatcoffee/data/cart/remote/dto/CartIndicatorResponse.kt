package com.rehat.rehatcoffee.data.cart.remote.dto

import com.google.gson.annotations.SerializedName

data class CartIndicatorResponse(
    @SerializedName("total_cart")val totalCart: Int
)