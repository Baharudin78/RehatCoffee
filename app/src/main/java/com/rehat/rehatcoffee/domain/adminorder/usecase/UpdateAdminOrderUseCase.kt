package com.rehat.rehatcoffee.domain.adminorder.usecase

import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminOrderResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.repository.AdminOrderRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAdminOrderUseCase @Inject constructor(
    private val adminOrderRepository: AdminOrderRepository
) {
    suspend fun updateOrderAdmin(
        id: String,
        orderStatus: Boolean,
        payStatus: Boolean
    ) : Flow<BaseResult<AdminOrderEntity, WrappedResponse<AdminOrderResponse>>>{
        return adminOrderRepository.updateOrder(id, orderStatus, payStatus)
    }
}