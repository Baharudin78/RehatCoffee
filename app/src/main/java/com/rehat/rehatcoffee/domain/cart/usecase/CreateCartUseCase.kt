package com.rehat.rehatcoffee.domain.cart.usecase

import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CreateCartRequest
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.domain.cart.repository.CartRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend fun createCart(productId: CreateCartRequest): Flow<BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>> {
        return repository.createCart(productId)
    }
}