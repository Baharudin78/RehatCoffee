package com.rehat.rehatcoffee.domain.adminorder.repository

import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminOrderResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.adminorder.entity.AdminOrderEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface AdminOrderRepository {
    suspend fun getAdminOrder(orderStatus : Boolean,isAdmin : Boolean) : Flow<BaseResult<List<AdminOrderEntity>, WrappedListResponse<AdminOrderResponse>>>
    suspend fun updateOrderPayment(id :String,payStatus : Boolean) : Flow<BaseResult<AdminOrderEntity, WrappedResponse<AdminOrderResponse>>>
    suspend fun updateOrderStatus(id :String,orderStatus: Boolean) : Flow<BaseResult<AdminOrderEntity, WrappedResponse<AdminOrderResponse>>>
}