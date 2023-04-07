package com.rehat.rehatcoffee.data.cart

import com.rehat.rehatcoffee.data.cart.remote.api.CartApi
import com.rehat.rehatcoffee.data.cart.repository.CartRepositoryImpl
import com.rehat.rehatcoffee.data.common.module.NetworkModule
import com.rehat.rehatcoffee.domain.cart.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class CartModule {

    @Singleton
    @Provides
    fun provideCartApi(retrofit: Retrofit): CartApi {
        return retrofit.create(CartApi::class.java)
    }

    @Provides
    @Singleton
    fun providesCartRepository(cartApi: CartApi): CartRepository {
        return CartRepositoryImpl(cartApi)
    }
}