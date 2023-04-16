package com.rehat.rehatcoffee.data.adminorder.remote.api

import com.rehat.rehatcoffee.core.Constants.ORDER_ADMIN
import com.rehat.rehatcoffee.data.adminorder.remote.dto.AdminOrderResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface AdminOrderApi {

    @GET(ORDER_ADMIN)
    suspend fun getOrderAdmin(
        @Query("order_status")orderStatus : Boolean
    ) : Response<WrappedListResponse<AdminOrderResponse>>

    @PUT(ORDER_ADMIN)
    suspend fun updateOrder(
        @Query("id") id : String,
        @Field("order_status") orderStatus: Boolean,
        @Field("pay_status") payStatus: Boolean,
    ) : Response<WrappedResponse<AdminOrderResponse>>
}