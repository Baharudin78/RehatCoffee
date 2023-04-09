package com.rehat.rehatcoffee.data.notification.remote.mapper

import com.rehat.rehatcoffee.data.notification.remote.dto.NotifIndicationResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotificationResponse
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotifIndicatorEntity
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotificationEntity

fun NotificationResponse.toNotificationEntity(): NotificationEntity{
    return NotificationEntity(
        id, forUser, message, read
    )
}

fun NotifIndicationResponse.toNotifIndicatorEntity(): NotifIndicatorEntity {
    return NotifIndicatorEntity(
        indicator
    )
}