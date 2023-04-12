package com.rehat.rehatcoffee.data.product.remote.dto


import com.google.gson.annotations.SerializedName

data class ImageProductResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("public_id")
    val publicId: String,
    @SerializedName("url")
    val url: String
)