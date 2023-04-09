package com.rehat.rehatcoffee.domain.notificaitation.entity


data class NotificationEntity(
    val id: String? = null,
    val forUser: String? = null,
    val message: String? = null,
    val read: Boolean? = null,
)
