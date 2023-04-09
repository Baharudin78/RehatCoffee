package com.rehat.rehatcoffee.domain.notificaitation.usecase

import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotificationResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotificationEntity
import com.rehat.rehatcoffee.domain.notificaitation.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotifUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend fun getNotif() : Flow<BaseResult<List<NotificationEntity>, WrappedListResponse<NotificationResponse>>>{
        return notificationRepository.getNotification()
    }
}