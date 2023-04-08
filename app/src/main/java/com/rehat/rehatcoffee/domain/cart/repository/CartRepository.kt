package com.rehat.rehatcoffee.domain.cart.repository

import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CartIndicatorResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CreateCartRequest
import com.rehat.rehatcoffee.data.cart.remote.dto.GetCartResponse
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.domain.cart.entity.GetCartEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun getCart(): Flow<BaseResult<GetCartEntity, WrappedResponse<GetCartResponse>>>
    suspend fun createCart(productId: String): Flow<BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>>
    suspend fun updateCart(
        id: String,
        qty: Int
    ): Flow<BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>>

    suspend fun deleteCart(id: String): Flow<BaseResult<MessageResponse, MessageResponse>>

    suspend fun cartIndicator(): Flow<BaseResult<CartIndicatorEntity, WrappedResponse<CartIndicatorResponse>>>
}