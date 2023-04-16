package com.rehat.rehatcoffee.data.adminorder.remote.dto


import com.google.gson.annotations.SerializedName

data class AdminProductOrderResponse(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("product")
    val product: AdminProductItemOrderResponse? = null,
    @SerializedName("qty")
    val qty: Int? = null,
    @SerializedName("total_price")
    val totalPrice: Int? = null,
)