package com.rehat.rehatcoffee.data.adminorder.remote.dto


import com.google.gson.annotations.SerializedName

data class AdminProductItemOrderResponse(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("images")
    val images: List<ImageProductResponse?>,
    @SerializedName("kinds")
    val kinds: String? = null,
    @SerializedName("price")
    val price: Int? = null,
    @SerializedName("product_name")
    val productName: String? = null
)