package com.fridge.features.detailsItem.data.di

import com.fridge.features.detailsItem.data.DetailItemRepositoryImpl
import com.fridge.features.detailsItem.domain.DetailItemRepository
import com.fridge.features.detailsItem.domain.LocaleDetailItemSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailItemRepositoryModule {

    @Provides
    @Singleton
    fun provideDetailItemRepository(
        localeSource: LocaleDetailItemSource
    ): DetailItemRepository = DetailItemRepositoryImpl(localeSource)

}