package com.rehat.rehatcoffee.data.order

import com.rehat.rehatcoffee.data.common.module.NetworkModule
import com.rehat.rehatcoffee.data.order.remote.api.OrderApi
import com.rehat.rehatcoffee.data.order.repository.OrderRepositoryImpl
import com.rehat.rehatcoffee.domain.order.repository.OrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class OrderModule {

    @Provides
    @Singleton
    fun provideOrderId(retrofit: Retrofit): OrderApi {
        return retrofit.create(OrderApi::class.java)
    }

    @Provides
    @Singleton
    fun providerOrderRepository(orderApi : OrderApi): OrderRepository{
        return OrderRepositoryImpl(orderApi)
    }
}