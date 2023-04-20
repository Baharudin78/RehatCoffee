package com.rehat.rehatcoffee.data.adminorder.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.adminorder.remote.api.AdminOrderApi
import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminOrderResponse
import com.rehat.rehatcoffee.data.adminorder.remote.mapper.toOrderAdminEntity
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.repository.AdminOrderRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class AdminOrderRepositoryImpl @Inject constructor(
    private val adminOrderApi: AdminOrderApi
) : AdminOrderRepository{
    override suspend fun getAdminOrder(orderStatus: Boolean, isAdmin : Boolean): Flow<BaseResult<List<AdminOrderEntity>, WrappedListResponse<AdminOrderResponse>>> {
        return flow {
            val response = adminOrderApi.getOrderAdmin(orderStatus, isAdmin)
            if (response.isSuccessful){
                val body = response.body()!!
                val order = mutableListOf<AdminOrderEntity>()
                body.data?.forEach { orderAdmin ->
                    order.add(
                        orderAdmin.toOrderAdminEntity()
                    )
                }
                emit(BaseResult.Success(order))
            }else{
                val type = object : TypeToken<WrappedListResponse<AdminOrderResponse>>(){}.type
                val err = Gson().fromJson<WrappedListResponse<AdminOrderResponse>>(response.errorBody()!!.charStream(), type)!!
                err.status = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }

    override suspend fun updateOrderPayment(
        id: String,
        payStatus: Boolean
    ): Flow<BaseResult<AdminOrderEntity, WrappedResponse<AdminOrderResponse>>> {
        return flow {
            try {
                val updateOrderResponse = adminOrderApi.updateOrderPayment(id, payStatus)
                if (updateOrderResponse.isSuccessful) {
                    val body = updateOrderResponse.body()
                    if (body != null) {
                        val updateOrder = body.data?.toOrderAdminEntity()
                        if (updateOrder != null) {
                            emit(BaseResult.Success(updateOrder))
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
                    val errorResponse = parseErrorResponse(updateOrderResponse)
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

    override suspend fun updateOrderStatus(
        id: String,
        orderStatus: Boolean
    ): Flow<BaseResult<AdminOrderEntity, WrappedResponse<AdminOrderResponse>>> {
        return flow {
            try {
                val updateOrderResponse = adminOrderApi.updateOrderStatus(id, orderStatus)
                if (updateOrderResponse.isSuccessful) {
                    val body = updateOrderResponse.body()
                    if (body != null) {
                        val updateOrder = body.data?.toOrderAdminEntity()
                        if (updateOrder != null) {
                            emit(BaseResult.Success(updateOrder))
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
                    val errorResponse = parseErrorResponse(updateOrderResponse)
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

    private fun parseErrorResponse(response: Response<*>): WrappedResponse<AdminOrderResponse> {
        val type = object : TypeToken<WrappedResponse<AdminOrderResponse>>() {}.type
        val errorBody =
            response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
        val errorResponse =
            Gson().fromJson<WrappedResponse<AdminOrderResponse>>(errorBody.charStream(), type)!!
        errorResponse.status = response.code()
        return errorResponse
    }
}