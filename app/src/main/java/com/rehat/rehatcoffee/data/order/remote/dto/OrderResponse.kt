package com.rehat.rehatcoffee.data.order.remote.dto

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("_id")val id: String? = null,
    @SerializedName("order_status")val orderStatus: Boolean? = null,
    @SerializedName("product")val product: List<ProductOrderListResponse?>,
    @SerializedName("total_price")val totalPrice: String? = null,
)