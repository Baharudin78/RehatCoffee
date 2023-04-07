package com.rehat.rehatcoffee.data.common.utils
import com.google.gson.annotations.SerializedName

data class WrappedResponse<T> (
    @SerializedName("message") var message : String,
    @SerializedName("statusCode") var status : Int?,
    @SerializedName("data") var data : T? = null,
)

data class WrappedListResponse<T> (
    @SerializedName("message") var message : String? = null,
    @SerializedName("status") var status : Int? = null,
    @SerializedName("data") var data : List<T>? = null
)

data class MessageResponse(
    val message: String? = null,
    val statusCode: Int? = null,
)