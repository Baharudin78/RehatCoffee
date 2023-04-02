package com.rehat.rehatcoffee.data.register.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")val name : String,
    @SerializedName("email")val email : String,
    @SerializedName("password")val password : String,
    @SerializedName("role")val role : String,
    @SerializedName("fcm_token")val fcmToken : String
)
