package com.rehat.rehatcoffee.domain.product.repository

import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProductRepository {
    suspend fun createProduct(
        param: MutableMap<String, @JvmSuppressWildcards RequestBody>,
        partFile: MultipartBody.Part?,
    ): Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>>

    suspend fun getProduct(kinds : String): Flow<BaseResult<List<ProductEntity>, WrappedListResponse<ProductResponse>>>

    suspend fun deleteProduct(id: String): Flow<BaseResult<MessageResponse, MessageResponse>>

    suspend fun updateProduct(
        id : String,
        param: MutableMap<String, @JvmSuppressWildcards RequestBody>,
        partFile: MultipartBody.Part?,
    ): Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>>


}