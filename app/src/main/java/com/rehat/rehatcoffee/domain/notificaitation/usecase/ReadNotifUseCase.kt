package com.rehat.rehatcoffee.domain.notificaitation.usecase

import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.notificaitation.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadNotifUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend fun readNotification(): Flow<BaseResult<MessageResponse, MessageResponse>>{
        return repository.readNotification()
    }
}