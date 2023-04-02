package com.rehat.rehatcoffee.data.login.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data") val user: UserResponse? = null,
    @SerializedName("token") val token: String? = null
)