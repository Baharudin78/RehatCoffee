package com.rehat.rehatcoffee.domain.notificaitation.repository

import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotifIndicationResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotificationResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotifIndicatorEntity
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun getNotification(): Flow<BaseResult<List<NotificationEntity>, WrappedListResponse<NotificationResponse>>>
    suspend fun readNotification(): Flow<BaseResult<MessageResponse, MessageResponse>>
    suspend fun getIndicator(): Flow<BaseResult<NotifIndicatorEntity, WrappedResponse<NotifIndicationResponse>>>
}