package com.rehat.rehatcoffee.data.notification.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.notification.remote.api.NotifApi
import com.rehat.rehatcoffee.data.notification.remote.dto.NotifIndicationResponse
import com.rehat.rehatcoffee.data.notification.remote.dto.NotificationResponse
import com.rehat.rehatcoffee.data.notification.remote.mapper.toNotifIndicatorEntity
import com.rehat.rehatcoffee.data.notification.remote.mapper.toNotificationEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotifIndicatorEntity
import com.rehat.rehatcoffee.domain.notificaitation.entity.NotificationEntity
import com.rehat.rehatcoffee.domain.notificaitation.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notifApi: NotifApi
) : NotificationRepository {
    override suspend fun getNotification(): Flow<BaseResult<List<NotificationEntity>, WrappedListResponse<NotificationResponse>>> {
        return flow {
            try {
                val response = notifApi.getNotification()
                if (response.isSuccessful){
                    val body = response.body()
                    val getNotif = mutableListOf<NotificationEntity>()
                    body?.data?.forEach { notificationResponse ->
                        getNotif.add(
                            notificationResponse.toNotificationEntity()
                        )
                    }
                    emit(BaseResult.Success(getNotif))
                }else {
                    val errorResponse = parseErrorResponse(response)
                    emit(BaseResult.Error(errorResponse))
                }
            }catch (e : Exception){
                emit(
                    BaseResult.Error(
                        WrappedListResponse(
                            message = e.message ?: "Error Occured",
                            status = null
                        )
                    )
                )
            }
        }
    }

    override suspend fun readNotification(): Flow<BaseResult<MessageResponse, MessageResponse>> {
        return flow {
            try {
                val readNotif = notifApi.readNotification()
                if (readNotif.isSuccessful) {
                    emit(BaseResult.Success(MessageResponse("Notif berhasil dibaca")))
                } else {
                    val errorResponse = readNotif.errorBody()?.string()
                    val errorMessage =
                        Gson().fromJson(errorResponse, MessageResponse::class.java).message
                    emit(BaseResult.Error(MessageResponse(errorMessage)))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(MessageResponse(e.message ?: "Unknown error occurred.")))
            }
        }
    }

    override suspend fun getIndicator(): Flow<BaseResult<NotifIndicatorEntity, WrappedResponse<NotifIndicationResponse>>> {
        return flow {
            try {
                val indicator = notifApi.getIndicator()
                if (indicator.isSuccessful) {
                    val body = indicator.body()
                    if (body != null) {
                        val notifIndicator = body.data?.toNotifIndicatorEntity()
                        if (notifIndicator != null) {
                            emit(BaseResult.Success(notifIndicator))
                        } else {
                            emit(
                                BaseResult.Error(
                                    WrappedResponse(
                                        message = "Empty response body",
                                        null
                                    )
                                )
                            )
                        }
                    } else {
                        emit(
                            BaseResult.Error(
                                WrappedResponse(
                                    message = "Null response body",
                                    null
                                )
                            )
                        )
                    }
                } else {
                    val errorResponse = parseIndicatorErrorResponse(indicator)
                    emit(BaseResult.Error(errorResponse))
                }
            } catch (e: Exception) {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message ?: "Error Occured",
                            status = null
                        )
                    )
                )
            }
        }
    }

    private fun parseErrorResponse(response: Response<*>): WrappedListResponse<NotificationResponse> {
        val type = object : TypeToken<WrappedResponse<NotificationResponse>>() {}.type
        val errorBody =
            response.errorBody() ?: return WrappedListResponse(message = "Unknown error occurred", null)
        val errorResponse =
            Gson().fromJson<WrappedListResponse<NotificationResponse>>(errorBody.charStream(), type)!!
        errorResponse.status = response.code()
        return errorResponse
    }

    private fun parseIndicatorErrorResponse(response: Response<*>): WrappedResponse<NotifIndicationResponse> {
        val type = object : TypeToken<WrappedResponse<NotifIndicationResponse>>() {}.type
        val errorBody =
            response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
        val errorResponse =
            Gson().fromJson<WrappedResponse<NotifIndicationResponse>>(errorBody.charStream(), type)!!
        errorResponse.status = response.code()
        return errorResponse
    }
}