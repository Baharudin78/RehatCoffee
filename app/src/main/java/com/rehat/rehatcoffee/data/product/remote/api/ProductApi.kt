package com.rehat.rehatcoffee.data.product.remote.api

import com.rehat.rehatcoffee.core.Constants.PRODUCT_ADMIN
import com.rehat.rehatcoffee.data.common.utils.MessageResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedListResponse
import com.rehat.rehatcoffee.data.common.utils.WrappedResponse
import com.rehat.rehatcoffee.data.product.remote.dto.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface ProductApi {
    @Multipart
    @POST(PRODUCT_ADMIN)
    suspend fun createProduct(
        @PartMap param: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part foto : MultipartBody.Part?,
    ) : Response<WrappedResponse<ProductResponse>>

    @Multipart
    @PUT(PRODUCT_ADMIN)
    suspend fun updateProduct(
        @Query("id") id : String,
        @PartMap param: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part foto : MultipartBody.Part?,
    ) : Response<WrappedResponse<ProductResponse>>

    @GET(PRODUCT_ADMIN)
    suspend fun getProduct(
        @Query("kinds") kinds : String
    ): Response<WrappedListResponse<ProductResponse>>

    @DELETE(PRODUCT_ADMIN)
    suspend fun deleteProduct(
        @Query("id") id : String
    ) : Response<MessageResponse>
}