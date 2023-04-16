package com.rehat.rehatcoffee.data.product.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import com.rehat.rehatcoffee.data.menu.remote.mapper.toImageMenuEntity
import com.rehat.rehatcoffee.data.product.remote.api.ProductApi
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import com.rehat.rehatcoffee.data.product.remote.mapper.convertImageResponseToEntity
import com.rehat.rehatcoffee.data.product.remote.mapper.toImageMenuEntity
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import com.rehat.rehatcoffee.domain.menu.entity.MenuEntity
import com.rehat.rehatcoffee.domain.product.entity.ProductEntity
import com.rehat.rehatcoffee.domain.product.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi : ProductApi
) : ProductRepository {
    override suspend fun createProduct(
        param: MutableMap<String, RequestBody>,
        partFile: MultipartBody.Part?
    ): Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>> {
        return flow {
            try {
                val response = if (partFile != null) {
                    productApi.createProduct(param, partFile)
                } else {
                    productApi.createProduct(param, null)
                }
                if (response.isSuccessful){
                    val body = response.body()!!
                    val image = body.data?.images?.map { it?.toImageMenuEntity() }
                    val letter = ProductEntity(
                        body.data?.description,
                        body.data?.id,
                        images = image!!,
                        body.data?.kinds,
                        body.data?.price,
                        body.data?.productName
                    )
                    emit(BaseResult.Success(letter))
                }else{
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        val type = object : TypeToken<WrappedResponse<ProductResponse>>(){}.type
                        val err = Gson().fromJson<WrappedResponse<ProductResponse>>(errorBody, type)!!
                        err.status = response.code()
                        emit(BaseResult.Error(err))
                    } else {
                        emit(BaseResult.Error(WrappedResponse("Unknown error",null)))
                    }
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(WrappedResponse(e.message ?: "Unknown error",null)))
            }
        }
    }


    override suspend fun getProduct(kinds : String): Flow<BaseResult<List<ProductEntity>, WrappedListResponse<ProductResponse>>> {
        return flow {
            val response = productApi.getProduct(kinds)
            if (response.isSuccessful){
                val body = response.body()!!
                val product = mutableListOf<ProductEntity>()
                body.data?.forEach { productReponse ->
                    product.add(
                        ProductEntity(
                            productReponse.id,
                            productReponse.description,
                            productReponse.images.map { it?.toImageMenuEntity() },
                            productReponse.kinds,
                            productReponse.price,
                            productReponse.productName
                        )
                    )
                }
                emit(BaseResult.Success(product))
            }else{
                val type = object : TypeToken<WrappedListResponse<ProductResponse>>(){}.type
                val err = Gson().fromJson<WrappedListResponse<ProductResponse>>(response.errorBody()!!.charStream(), type)!!
                err.status = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }

    override suspend fun deleteProduct(id: String): Flow<BaseResult<MessageResponse, MessageResponse>> {
        return flow {
            try {
                val deleteResponse = productApi.deleteProduct(id)
                if (deleteResponse.isSuccessful) {
                    emit(BaseResult.Success(MessageResponse("Product item deleted successfully.")))
                } else {
                    val errorResponse = deleteResponse.errorBody()?.string()
                    val errorMessage =
                        Gson().fromJson(errorResponse, MessageResponse::class.java).message
                    emit(BaseResult.Error(MessageResponse(errorMessage)))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(MessageResponse(e.message ?: "Unknown error occurred.")))
            }
        }
    }

    override suspend fun updateProduct(
        id: String,
        param: MutableMap<String, RequestBody>,
        partFile: MultipartBody.Part?
    ): Flow<BaseResult<ProductEntity, WrappedResponse<ProductResponse>>> {
        return flow {
            try {
                val response = if (partFile != null) {
                    productApi.updateProduct(id,param, partFile)
                } else {
                    productApi.updateProduct(id,param, null)
                }
                if (response.isSuccessful){
                    val body = response.body()!!
                    val image = body.data?.images
                    val letter = ProductEntity(
                        body.data?.description,
                        body.data?.id,
                        images = image!!.map { it?.toImageMenuEntity() },
                        body.data?.kinds,
                        body.data?.price,
                        body.data?.productName
                    )
                    emit(BaseResult.Success(letter))
                }else{
                    val type = object : TypeToken<WrappedResponse<ProductResponse>>(){}.type
                    val err = Gson().fromJson<WrappedResponse<ProductResponse>>(response.errorBody()!!.charStream(), type)!!
                    err.status = response.code()
                    emit(BaseResult.Error(err))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(WrappedResponse<ProductResponse>( e.message ?: "Unknown error",null)))
            }
        }
    }
}