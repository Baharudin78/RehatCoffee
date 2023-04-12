package com.rehat.rehatcoffee.domain.product.usecase

import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repository : ProductRepository
) {
    suspend fun getProduct(kinds : String) : Flow<BaseResult<List<ProductEntity>, WrappedListResponse<ProductResponse>>>{
        return repository.getProduct(kinds)
    }
}