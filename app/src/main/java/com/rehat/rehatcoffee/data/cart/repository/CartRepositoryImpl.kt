package com.rehat.rehatcoffee.data.cart.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rehat.rehatcoffee.data.cart.remote.api.CartApi
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CartIndicatorResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CreateCartRequest
import com.rehat.rehatcoffee.data.cart.remote.dto.GetCartResponse
import com.rehat.rehatcoffee.data.cart.remote.mapper.toCartDataEntity
import com.rehat.rehatcoffee.data.cart.remote.mapper.toCartIndicatorEntity
import com.rehat.rehatcoffee.data.cart.remote.mapper.toGetCartEntity
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.domain.cart.entity.CartDataEntity
import com.rehat.rehatcoffee.domain.cart.entity.CartIndicatorEntity
import com.rehat.rehatcoffee.domain.cart.entity.GetCartEntity
import com.rehat.rehatcoffee.domain.cart.repository.CartRepository
import com.rehat.rehatcoffee.domain.common.base.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApi: CartApi
) : CartRepository {

    override suspend fun getCart(): Flow<BaseResult<GetCartEntity, WrappedResponse<GetCartResponse>>> {
        return flow {
            try {
                val getCartResponse = cartApi.getCart()
                if (getCartResponse.isSuccessful) {
                    val body = getCartResponse.body()
                    if (body != null) {
                        val getCart = body.data?.toGetCartEntity()
                        if (getCart != null) {
                            emit(BaseResult.Success(getCart))
                        } else {
                            emit(
                                BaseResult.Error(
                                    WrappedResponse(
                                        message = "Empty response body",
                                        null
                                    )
                                )
                            )
                        }
                    } else {
                        emit(
                            BaseResult.Error(
                                WrappedResponse(
                                    message = "Null response body",
                                    null
                                )
                            )
                        )
                    }
                } else {
                    val errorResponse = parseErrorResponse(getCartResponse)
                    emit(BaseResult.Error(errorResponse))
                }
            } catch (e: Exception) {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message ?: "Error Occured",
                            status = null
                        )
                    )
                )
            }
        }
    }


    override suspend fun createCart(productId: String): Flow<BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>> {
        return flow {
            try {
                val createResponse = cartApi.createCart(productId)
                if (createResponse.isSuccessful) {
                    val body = createResponse.body()
                    if (body != null) {
                        val createCart = body.data?.toCartDataEntity()
                        if (createCart != null) {
                            emit(BaseResult.Success(createCart))
                        } else {
                            emit(
                                BaseResult.Error(
                                    WrappedResponse(
                                        message = "Empty response body",
                                        null
                                    )
                                )
                            )
                        }
                    } else {
                        emit(
                            BaseResult.Error(
                                WrappedResponse(
                                    message = "Null response body",
                                    null
                                )
                            )
                        )
                    }
                } else {
                    val errorResponse = parseCartErrorResponse(createResponse)
                    emit(BaseResult.Error(errorResponse))
                }
            } catch (e: Exception) {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message ?: "Error Occured",
                            status = null
                        )
                    )
                )
            }
        }
    }

    override suspend fun updateCart(
        id: String,
        qty: Int
    ): Flow<BaseResult<CartDataEntity, WrappedResponse<CartDataResponse>>> {
        return flow {
            try {
                val updateResponse = cartApi.updateCart(id, qty)
                if (updateResponse.isSuccessful) {
                    val body = updateResponse.body()
                    if (body != null) {
                        val updateCart = body.data?.toCartDataEntity()
                        if (updateCart != null) {
                            emit(BaseResult.Success(updateCart))
                        } else {
                            emit(
                                BaseResult.Error(
                                    WrappedResponse(
                                        message = "Empty response body",
                                        null
                                    )
                                )
                            )
                        }
                    } else {
                        emit(
                            BaseResult.Error(
                                WrappedResponse(
                                    message = "Null response body",
                                    null
                                )
                            )
                        )
                    }
                } else {
                    val errorResponse = parseCartErrorResponse(updateResponse)
                    emit(BaseResult.Error(errorResponse))
                }
            } catch (e: Exception) {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message ?: "Error Occured",
                            status = null
                        )
                    )
                )
            }
        }
    }

    override suspend fun deleteCart(id: String): Flow<BaseResult<MessageResponse, MessageResponse>> {
        return flow {
            try {
                val deleteResponse = cartApi.deleteCart(id)
                if (deleteResponse.isSuccessful) {
                    emit(BaseResult.Success(MessageResponse("Cart item deleted successfully.")))
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

    override suspend fun cartIndicator(): Flow<BaseResult<CartIndicatorEntity, WrappedResponse<CartIndicatorResponse>>> {
        return flow {
            try {
                val indicator = cartApi.getCartIndidator()
                if (indicator.isSuccessful) {
                    val body = indicator.body()
                    if (body != null) {
                        val cartIndicator = body.data?.toCartIndicatorEntity()
                        if (cartIndicator != null) {
                            emit(BaseResult.Success(cartIndicator))
                        } else {
                            emit(
                                BaseResult.Error(
                                    WrappedResponse(
                                        message = "Empty response body",
                                        null
                                    )
                                )
                            )
                        }
                    } else {
                        emit(
                            BaseResult.Error(
                                WrappedResponse(
                                    message = "Null response body",
                                    null
                                )
                            )
                        )
                    }
                } else {
                    val errorResponse = parseIndicatorErrorResponse(indicator)
                    emit(BaseResult.Error(errorResponse))
                }
            } catch (e: Exception) {
                emit(
                    BaseResult.Error(
                        WrappedResponse(
                            message = e.message ?: "Error Occured",
                            status = null
                        )
                    )
                )
            }
        }
    }


    private fun parseErrorResponse(response: Response<*>): WrappedResponse<GetCartResponse> {
        val type = object : TypeToken<WrappedResponse<GetCartResponse>>() {}.type
        val errorBody =
            response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
        val errorResponse =
            Gson().fromJson<WrappedResponse<GetCartResponse>>(errorBody.charStream(), type)!!
        errorResponse.status = response.code()
        return errorResponse
    }

    private fun parseCartErrorResponse(response: Response<*>): WrappedResponse<CartDataResponse> {
        val type = object : TypeToken<WrappedResponse<CartDataResponse>>() {}.type
        val errorBody =
            response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
        val errorResponse =
            Gson().fromJson<WrappedResponse<CartDataResponse>>(errorBody.charStream(), type)!!
        errorResponse.status = response.code()
        return errorResponse
    }

    private fun parseIndicatorErrorResponse(response: Response<*>): WrappedResponse<CartIndicatorResponse> {
        val type = object : TypeToken<WrappedResponse<CartIndicatorResponse>>() {}.type
        val errorBody =
            response.errorBody() ?: return WrappedResponse(message = "Unknown error occurred", null)
        val errorResponse =
            Gson().fromJson<WrappedResponse<CartIndicatorResponse>>(errorBody.charStream(), type)!!
        errorResponse.status = response.code()
        return errorResponse
    }
}