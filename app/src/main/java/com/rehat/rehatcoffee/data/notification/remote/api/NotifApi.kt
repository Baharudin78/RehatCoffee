package com.rehat.rehatcoffee.data.notification.remote.api

import com.rehat.rehatcoffee.core.Constants.GET_NOTIFICATION
import com.rehat.rehatcoffee.core.Constants.INDICATOR_NOTIF
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotifIndicationResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT

interface NotifApi {
    @GET(GET_NOTIFICATION)
    suspend fun getNotification(): Response<WrappedListResponse<NotificationResponse>>

    @PUT(GET_NOTIFICATION)
    suspend fun readNotification(): Response<MessageResponse>

    @GET(INDICATOR_NOTIF)
    suspend fun getIndicator(): Response<WrappedResponse<NotifIndicationResponse>>
}