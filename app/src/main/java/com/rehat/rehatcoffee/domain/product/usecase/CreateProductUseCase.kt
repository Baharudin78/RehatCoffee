package com.rehat.rehatcoffee.domain.product.usecase

import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend fun createProduct(param : MutableMap<String, @JvmSuppressWildcards RequestBody>, partFile : MultipartBody.Part?) : Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>> {
        return repository.createProduct(param, partFile)
    }
}