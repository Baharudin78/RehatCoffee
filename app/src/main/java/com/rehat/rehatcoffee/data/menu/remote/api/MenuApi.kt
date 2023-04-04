package com.rehat.rehatcoffee.data.menu.remote.api

import com.rehat.rehatcoffee.core.Constants.PRODUCTS_DRINK
import com.rehat.rehatcoffee.core.Constants.PRODUCTS_FOOD
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.menu.remote.dto.MenuResponse
import retrofit2.Response
import retrofit2.http.GET

interface MenuApi {
    @GET(PRODUCTS_DRINK)
    suspend fun getMenuDrink(): Response<WrappedListResponse<MenuResponse>>

    @GET(PRODUCTS_FOOD)
    suspend fun getMenuFood(): Response<WrappedListResponse<MenuResponse>>
}