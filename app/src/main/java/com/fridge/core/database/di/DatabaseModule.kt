package com.fridge.core.database.di

import android.content.Context
import androidx.room.Room
import com.fridge.core.database.FridgeDatabase
import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.sources.RoomAllItemsSourceImpl
import com.fridge.features.allItems.domain.LocaleAllItemsSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FridgeDatabase =
        Room.databaseBuilder(
            context,
            FridgeDatabase::class.java,
            "FridgeDatabase"
        ).build()

    @Provides
    fun provideFoodDao(db: FridgeDatabase): FridgeItemDao = db.fridgeItemDao

    @Provides
    fun provideAllItemsSource(
        fridgeItemDao: FridgeItemDao
    ): LocaleAllItemsSource = RoomAllItemsSourceImpl(fridgeItemDao)

}