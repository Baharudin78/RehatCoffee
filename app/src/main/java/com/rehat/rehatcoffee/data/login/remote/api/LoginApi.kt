package com.rehat.rehatcoffee.data.login.remote.api

import com.rehat.rehatcoffee.core.Constants.LOGIN_URL
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.login.remote.dto.LoginRequest
import com.rehat.rehatcoffee.data.login.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST(LOGIN_URL)
    suspend fun login(@Body loginRequest: LoginRequest): Response<WrappedResponse<LoginResponse>>
}