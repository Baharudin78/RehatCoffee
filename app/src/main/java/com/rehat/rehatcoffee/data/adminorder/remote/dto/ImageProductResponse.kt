package com.rehat.rehatcoffee.data.adminorder.remote.dto


import com.google.gson.annotations.SerializedName

data class ImageProductResponse(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("public_id")
    val publicId: String? = null,
    @SerializedName("url")
    val url: String? = null
)