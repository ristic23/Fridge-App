package com.fridge.features.allItems.data.di

import com.fridge.features.allItems.data.AllItemsRepositoryImpl
import com.fridge.features.allItems.domain.AllItemsRepository
import com.fridge.features.allItems.domain.LocaleAllItemsSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AllItemsRepositoryModule {

    @Provides
    @Singleton
    fun bindAllItemsRepository(
        localeSource: LocaleAllItemsSource
    ): AllItemsRepository = AllItemsRepositoryImpl(localeSource)

}