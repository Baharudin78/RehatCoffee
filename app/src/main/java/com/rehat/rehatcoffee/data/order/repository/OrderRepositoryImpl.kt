package com.rehat.rehatcoffee.data.order.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import com.rehat.rehatcoffee.data.order.remote.api.OrderApi
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import com.rehat.rehatcoffee.data.order.remote.mapper.toOrderEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.domain.order.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderApi: OrderApi
) : OrderRepository {
    override suspend fun createOrder(): Flow<BaseResult<OrderEntity, WrappedResponse<OrderResponse>>> {
        return flow {
            try {
                val result = orderApi.createOrder()
                if (result.isSuccessful) {
                    val body = result.body()
                    if (body != null) {
                        val creatOrder = body.data?.toOrderEntity()
                        if (creatOrder != null) {
                            emit(BaseResult.Success(creatOrder))
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
                    val errorResponse = parseCartErrorResponse(result)
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

    override suspend fun getOrder(): Flow<BaseResult<List<OrderEntity>, WrappedListResponse<OrderResponse>>> {
        return flow {
            try {
                val response = orderApi.getOrder()
                if (response.isSuccessful){
                    val body = response.body()
                    val getOrder = mutableListOf<OrderEntity>()
                    body?.data?.forEach { orderResponse ->
                        getOrder.add(
                            orderResponse.toOrderEntity()
                        )
                    }
                    emit(BaseResult.Success(getOrder))
                }else{
                    val type = object : TypeToken<WrappedListResponse<OrderResponse>>(){}.type
                    val err = Gson().fromJson<WrappedListResponse<OrderResponse>>(response.errorBody()!!.charStream(), type)!!
                    err.status = response.code()
                    emit(BaseResult.Error(err))
                }
            }catch (e : Exception){
                emit(
                    BaseResult.Error(
                        WrappedListResponse(
                            message = e.message ?: "Error occured"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getOrderDetail(id: String): Flow<BaseResult<OrderEntity, WrappedResponse<OrderResponse>>> {
        return flow {
            try {
                val response = orderApi.getOrderDetail(id)
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        val getOrderById = body.data?.toOrderEntity()
                        if (getOrderById != null){
                            emit(BaseResult.Success(getOrderById))
                        }else {
                            emit(
                                BaseResult.Error(
                                    WrappedResponse(
                                        message = "Empty response body",
                                        null
                                    )
                                )
                            )
                        }
                    }else {
                        emit(
                            BaseResult.Error(
                                WrappedResponse(
                                    message = "Null response body",
                                    null
                                )
                            )
                        )
                    }
                }else {
                    val errorResponse = parseCartErrorResponse(response)
                    emit(BaseResult.Error(errorResponse))
                }
            }catch (e: Exception){
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

    private fun parseCartErrorResponse(response: Response<*>): WrappedResponse<OrderResponse> {
        val type = object : TypeToken<WrappedResponse<OrderResponse>>() {}.type
        val errorBody =
            response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
        val errorResponse =
            Gson().fromJson<WrappedResponse<OrderResponse>>(errorBody.charStream(), type)!!
        errorResponse.status = response.code()
        return errorResponse
    }
}