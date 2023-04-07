package com.rehat.rehatcoffee.domain.cart.usecase

import com.rehat.rehatcoffee.data.cart.remote.dto.GetCartResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.cart.entity.GetCartEntity
import com.rehat.rehatcoffee.domain.cart.repository.CartRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun getCart(): Flow<BaseResult<GetCartEntity, WrappedResponse<GetCartResponse>>>{
        return cartRepository.getCart()
    }
}