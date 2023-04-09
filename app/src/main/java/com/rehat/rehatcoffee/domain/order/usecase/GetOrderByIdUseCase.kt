package com.rehat.rehatcoffee.domain.order.usecase

import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.domain.order.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderByIdUseCase @Inject constructor(
    private val repository : OrderRepository
) {
    suspend fun getOrderDetail(id : String) : Flow<BaseResult<OrderEntity, WrappedResponse<OrderResponse>>>{
        return repository.getOrderDetail(id)
    }
}