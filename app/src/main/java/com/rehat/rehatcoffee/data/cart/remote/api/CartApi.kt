package com.rehat.rehatcoffee.data.cart.remote.api

import com.rehat.rehatcoffee.core.Constants.CART_URL
import com.rehat.rehatcoffee.data.cart.remote.dto.CartDataResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.CreateCartRequest
import com.rehat.rehatcoffee.data.cart.remote.dto.GetCartResponse
import com.rehat.rehatcoffee.data.cart.remote.dto.ProductCartResponse
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface CartApi {
    @GET(CART_URL)
    suspend fun getCart(): Response<WrappedResponse<GetCartResponse>>

    @POST(CART_URL)
    suspend fun createCart(@Body productId: CreateCartRequest): Response<WrappedResponse<CartDataResponse>>

    @PUT(CART_URL)
    suspend fun updateCart(
        @Query("id") id: String,
        @Body qty: CreateCartRequest
    ): Response<WrappedResponse<CartDataResponse>>

    @DELETE(CART_URL)
    suspend fun deleteCart(
        @Query("id") id: String
    ): Response<MessageResponse>
}