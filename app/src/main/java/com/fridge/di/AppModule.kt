package com.fridge.di

import android.content.Context
import android.content.SharedPreferences
import com.fridge.core.data.Cache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("fridgeSharedPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideCache(sharedPreferences: SharedPreferences): Cache {
        return Cache(sharedPreferences)
    }

}