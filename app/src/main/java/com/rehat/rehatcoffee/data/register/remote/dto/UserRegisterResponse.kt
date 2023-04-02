package com.rehat.rehatcoffee.data.register.remote.dto

import com.google.gson.annotations.SerializedName

data class UserRegisterResponse(
    @SerializedName("email") val email: String? = null,
    @SerializedName("fcm_token") val fcmToken: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("role") val role: String? = null,
)
