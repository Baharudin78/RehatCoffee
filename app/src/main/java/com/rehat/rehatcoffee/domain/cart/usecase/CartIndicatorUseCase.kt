package com.rehat.rehatcoffee.domain.cart.usecase

import com.rehat.rehatcoffee.data.cart.remote.dto.CartIndicatorResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.domain.cart.repository.CartRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartIndicatorUseCase @Inject constructor(
    private val repository : CartRepository
) {
    suspend fun getCartIndicator():Flow<BaseResult<CartIndicatorEntity, WrappedResponse<CartIndicatorResponse>>>{
        return repository.cartIndicator()
    }
}