package com.rehat.rehatcoffee.data.cart.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductCartResponse(
    @SerializedName("_id")val id: String? = null,
    @SerializedName("images")val images: List<ImageCartResponse?>,
    @SerializedName("kinds")val kinds: String? = null,
    @SerializedName("price")val price: Int? = null,
    @SerializedName("product_name")val productName: String? = null
)