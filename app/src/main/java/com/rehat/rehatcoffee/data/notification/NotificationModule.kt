package com.rehat.rehatcoffee.data.notification

import com.rehat.rehatcoffee.data.common.module.NetworkModule
import com.rehat.rehatcoffee.data.notification.remote.api.NotifApi
import com.rehat.rehatcoffee.data.notification.repository.NotificationRepositoryImpl
import com.rehat.rehatcoffee.domain.notificaitation.repository.NotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class NotificationModule {

    @Provides
    @Singleton
    fun provideNotifApi(retrofit : Retrofit): NotifApi{
        return retrofit.create(NotifApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRepository(notifApi : NotifApi): NotificationRepository{
        return NotificationRepositoryImpl(notifApi)
    }
}