package com.rehat.rehatcoffee.domain.notificaitation.usecase

import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotifIndicationResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotifIndicatorEntity
import com.rehat.rehatcoffee.domain.notificaitation.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotifIndicatorUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend fun getNotifIndicator(): Flow<BaseResult<NotifIndicatorEntity, WrappedResponse<NotifIndicationResponse>>>{
        return repository.getIndicator()
    }
}