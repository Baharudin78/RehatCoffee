package com.rehat.rehatcoffee.data.notification.remote.dto

import com.google.gson.annotations.SerializedName

data class NotifIndicationResponse(
    @SerializedName("indicator")val indicator : Boolean? = null
)
