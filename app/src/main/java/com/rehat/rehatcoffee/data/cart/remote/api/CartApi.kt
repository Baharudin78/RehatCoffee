package com.rehat.rehatcoffee.data.cart.remote.api

import com.rehat.rehatcoffee.core.Constants.CART_INDICATOR
import com.rehat.rehatcoffee.core.Constants.CART_URL
import com.rehat.rehatcoffee.data.cart.remote.dto.*
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CartApi {
    @GET(CART_URL)
    suspend fun getCart(): Response<WrappedResponse<GetCartResponse>>

    @POST(CART_URL)
    @FormUrlEncoded
    suspend fun createCart(
        @Field("product") productId: String
    ): Response<WrappedResponse<CartDataResponse>>

    @PUT(CART_URL)
    @FormUrlEncoded
    suspend fun updateCart(
        @Query("id") id: String,
        @Field("qty") qty: Int
    ): Response<WrappedResponse<CartDataResponse>>

    @DELETE(CART_URL)
    suspend fun deleteCart(
        @Query("id") id: String
    ): Response<MessageResponse>

    @GET(CART_INDICATOR)
    suspend fun getCartIndidator() : Response<WrappedResponse<CartIndicatorResponse>>
}