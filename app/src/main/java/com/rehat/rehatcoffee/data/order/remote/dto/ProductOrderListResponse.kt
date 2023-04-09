package com.rehat.rehatcoffee.data.order.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductOrderListResponse(
    @SerializedName("_id")val id: String? = null,
    @SerializedName("product")val product: ProductOrderDetailResponse? = null,
    @SerializedName("qty")val qty: Int? = null,
    @SerializedName("total_price")val totalPrice: Int? = null,
)