package com.rehat.rehatcoffee.data.register

import com.rehat.rehatcoffee.data.common.module.NetworkModule
import com.rehat.rehatcoffee.data.register.remote.api.RegisterApi
import com.rehat.rehatcoffee.data.register.repository.RegisterRepositoryImpl
import com.rehat.rehatcoffee.domain.register.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class RegisterModule {

    @Singleton
    @Provides
    fun provideRegisterApi(retrofit: Retrofit): RegisterApi {
        return retrofit.create(RegisterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(registerApi: RegisterApi): RegisterRepository {
        return RegisterRepositoryImpl(registerApi)
    }
}