package com.rehat.rehatcoffee.data.login

import com.rehat.rehatcoffee.data.common.module.NetworkModule
import com.rehat.rehatcoffee.data.login.remote.api.LoginApi
import com.rehat.rehatcoffee.data.login.repository.LoginRepositoryImpl
import com.rehat.rehatcoffee.domain.login.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(loginApi: LoginApi) : LoginRepository {
        return LoginRepositoryImpl(loginApi)
    }
}