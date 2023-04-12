package com.rehat.rehatcoffee.domain.product.usecase

import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.domain.cart.repository.CartRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun deleteProduct(id: String): Flow<BaseResult<MessageResponse, MessageResponse>> {
        return repository.deleteProduct(id)
    }
}