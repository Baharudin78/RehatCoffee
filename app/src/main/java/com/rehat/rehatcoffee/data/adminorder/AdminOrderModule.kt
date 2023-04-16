package com.rehat.rehatcoffee.data.adminorder

import com.rehat.rehatcoffee.data.adminorder.remote.api.AdminOrderApi
import com.rehat.rehatcoffee.data.adminorder.repository.AdminOrderRepositoryImpl
import com.rehat.rehatcoffee.data.common.module.NetworkModule
import com.rehat.rehatcoffee.domain.adminorder.repository.AdminOrderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AdminOrderModule {

    @Provides
    @Singleton
    fun provideAdminOrderApi(retrofit: Retrofit) : AdminOrderApi{
        return retrofit.create(AdminOrderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAdminOrderRepo(adminOrderApi: AdminOrderApi) : AdminOrderRepository{
        return AdminOrderRepositoryImpl(adminOrderApi)
    }
}