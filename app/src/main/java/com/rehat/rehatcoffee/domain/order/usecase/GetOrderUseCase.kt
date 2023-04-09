package com.rehat.rehatcoffee.domain.order.usecase

import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.order.entity.OrderEntity
import com.rehat.rehatcoffee.domain.order.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend fun getOrder(): Flow<BaseResult<List<OrderEntity>, WrappedListResponse<OrderResponse>>>{
        return repository.getOrder()
    }
}