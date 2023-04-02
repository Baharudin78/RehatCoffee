package com.rehat.rehatcoffee.data.register.remote.dto

import com.google.gson.annotations.SerializedName
import com.rehat.rehatcoffee.data.login.remote.dto.UserResponse

data class RegisterResponse(
    @SerializedName("data")val register : UserRegisterResponse,
    @SerializedName("token")val token : String
)
