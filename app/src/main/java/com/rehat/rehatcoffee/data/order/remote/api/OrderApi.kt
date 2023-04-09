package com.rehat.rehatcoffee.data.order.remote.api

import com.rehat.rehatcoffee.core.Constants.CREATE_ORDER
import com.rehat.rehatcoffee.core.Constants.GET_ORDER
import com.rehat.rehatcoffee.core.Constants.ORDER_DETAIL
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.order.remote.dto.OrderResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApi {

    @POST(CREATE_ORDER)
    suspend fun createOrder(): Response<WrappedResponse<OrderResponse>>

    @GET(GET_ORDER)
    suspend fun getOrder() : Response<WrappedListResponse<OrderResponse>>

    @GET(ORDER_DETAIL)
    suspend fun getOrderDetail(
        @Query("id") id : String
    ): Response<WrappedResponse<OrderResponse>>
}