package com.rehat.rehatcoffee.data.product

import com.rehat.rehatcoffee.data.product.remote.api.ProductApi
import com.rehat.rehatcoffee.data.product.repository.ProductRepositoryImpl
import com.rehat.rehatcoffee.domain.product.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    @Singleton
    fun provideProductApi(retrofit : Retrofit) : ProductApi{
        return retrofit.create(ProductApi::class.java)
    }

    @Provides
    @Singleton
    fun providesProductRepository(productApi: ProductApi): ProductRepository{
        return ProductRepositoryImpl(productApi)
    }
}