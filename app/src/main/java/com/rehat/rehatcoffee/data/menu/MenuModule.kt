package com.rehat.rehatcoffee.data.menu

import com.rehat.rehatcoffee.data.common.module.NetworkModule
import com.rehat.rehatcoffee.data.menu.remote.api.MenuApi
import com.rehat.rehatcoffee.data.menu.repository.MenuRepositoryImpl
import com.rehat.rehatcoffee.domain.menu.repository.MenuRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class MenuModule {

    @Singleton
    @Provides
    fun provideMenuApi(retrofit: Retrofit): MenuApi {
        return retrofit.create(MenuApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMenuRepository(menuApi: MenuApi): MenuRepository {
        return MenuRepositoryImpl(menuApi)
    }
}