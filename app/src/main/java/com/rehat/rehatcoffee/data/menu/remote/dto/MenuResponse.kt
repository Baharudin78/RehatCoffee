package com.rehat.rehatcoffee.data.menu.remote.dto

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("_id") val id: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("images") val images: List<ImageMenuResponse?>,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("product_name") val productName: String? = null,
)