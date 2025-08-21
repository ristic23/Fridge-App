package com.fridge.features.addItem.data.di

import com.fridge.features.addItem.data.AddItemRepositoryImpl
import com.fridge.features.addItem.domain.AddItemRepository
import com.fridge.features.addItem.domain.LocaleAddItemSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddItemsRepositoryModule {

    @Provides
    @Singleton
    fun bindAllItemsRepository(
        localeSource: LocaleAddItemSource
    ): AddItemRepository = AddItemRepositoryImpl(localeSource)

}