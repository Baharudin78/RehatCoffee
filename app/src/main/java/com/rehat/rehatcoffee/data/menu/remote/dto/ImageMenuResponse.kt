package com.rehat.rehatcoffee.data.menu.remote.dto

import com.google.gson.annotations.SerializedName

data class ImageMenuResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("public_id") val publicId: String? = null,
    @SerializedName("url") val url: String? = null
)