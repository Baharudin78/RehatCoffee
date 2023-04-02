package com.rehat.rehatcoffee.data.login.remote.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("email") val email: String? = null,
    @SerializedName("fcm_token") val fcmToken: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("role") val role: String? = null,
)