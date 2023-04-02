package com.rehat.rehatcoffee.data.common.module

import android.content.Context
import com.rehat.rehatcoffee.core.TokenDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    fun provideSharedPref(@ApplicationContext context: Context): TokenDataStore {
        return TokenDataStore(context)
    }
}