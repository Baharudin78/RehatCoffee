package com.rehat.rehatcoffee.domain.adminorder.usecase

import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminOrderResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.adminorder.repository.AdminOrderRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAdminOrderUseCase @Inject constructor(
    private val adminOrderRepository: AdminOrderRepository
) {
    suspend fun getOrderAdmin(orderStatus :Boolean,isAdmin : Boolean) : Flow<BaseResult<List<AdminOrderEntity>, WrappedListResponse<AdminOrderResponse>>>{
        return adminOrderRepository.getAdminOrder(orderStatus, isAdmin)
    }
}