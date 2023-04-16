package com.rehat.rehatcoffee.data.adminorder.remote.dto


import com.google.gson.annotations.SerializedName

data class AdminOrderResponse(
    @SerializedName("_id")
    val id: String,
    @SerializedName("order_status")
    val orderStatus: Boolean,
    @SerializedName("pay_status")
    val payStatus: Boolean,
    @SerializedName("product")
    val product: List<AdminProductOrderResponse>,
    @SerializedName("total_price")
    val totalPrice: Int,
)