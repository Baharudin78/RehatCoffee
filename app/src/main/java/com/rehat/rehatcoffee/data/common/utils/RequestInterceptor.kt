package com.rehat.rehatcoffee.data.common.utils

import com.rehat.rehatcoffee.core.TokenDataStore
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(
    private val pref : TokenDataStore
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = pref.userTokenFlow
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}