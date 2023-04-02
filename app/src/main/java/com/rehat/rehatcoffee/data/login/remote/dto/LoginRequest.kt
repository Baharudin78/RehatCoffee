package com.rehat.rehatcoffee.data.login.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("fcm_token") val fcmToken: String
)
