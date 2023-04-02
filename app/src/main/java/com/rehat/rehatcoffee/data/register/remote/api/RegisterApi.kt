package com.rehat.rehatcoffee.data.register.remote.api

import com.rehat.rehatcoffee.core.Constants.REGISTER_URL
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterRequest
import com.rehat.rehatcoffee.data.register.remote.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST(REGISTER_URL)
    suspend fun register(@Body registerRequest : RegisterRequest) : Response<WrappedResponse<RegisterResponse>>
}