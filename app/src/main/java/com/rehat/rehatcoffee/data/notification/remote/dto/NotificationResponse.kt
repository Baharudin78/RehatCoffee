package com.rehat.rehatcoffee.data.notification.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("_id")val id: String? = null,
    @SerializedName("for_user")val forUser: String? = null,
    @SerializedName("message")val message: String? = null,
    @SerializedName("read")val read: Boolean? = null,
)