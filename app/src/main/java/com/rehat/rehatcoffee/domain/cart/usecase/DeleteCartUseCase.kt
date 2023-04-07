package com.rehat.rehatcoffee.domain.cart.usecase

import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.domain.cart.repository.CartRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend fun deleteCart(id: String): Flow<BaseResult<MessageResponse, MessageResponse>> {
        return repository.deleteCart(id)
    }
}