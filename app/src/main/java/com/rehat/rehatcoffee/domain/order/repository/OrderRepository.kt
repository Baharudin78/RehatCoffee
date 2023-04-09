package com.rehat.rehatcoffee.domain.order.repository

import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun createOrder(): Flow<BaseResult<OrderEntity, WrappedResponse<OrderResponse>>>
    suspend fun getOrder():Flow<BaseResult<List<OrderEntity>, WrappedListResponse<OrderResponse>>>
    suspend fun getOrderDetail(id : String) : Flow<BaseResult<OrderEntity, WrappedResponse<OrderResponse>>>
}