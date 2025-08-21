package com.fridge.core.database.di

import android.content.Context
import androidx.room.Room
import com.fridge.core.database.FridgeDatabase
import com.fridge.core.database.converter.TypeConverts
import com.fridge.core.database.dao.FridgeItemDao
import com.fridge.core.database.sources.RoomAllItemsSourceImpl
import com.fridge.core.database.sources.RoomDetailItemSourceImpl
import com.fridge.features.allItems.domain.LocaleAllItemsSource
import com.fridge.features.detailsItem.domain.LocaleDetailItemSource
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
    fun provideDatabase(
        @ApplicationContext context: Context,
        typeConverts: TypeConverts
    ): FridgeDatabase =
        Room.databaseBuilder(
            context,
            FridgeDatabase::class.java,
            "FridgeDatabase"
        )
            .addTypeConverter(typeConverts)
            .build()

    @Provides
    fun provideFoodDao(db: FridgeDatabase): FridgeItemDao = db.fridgeItemDao

    @Provides
    fun provideAllItemsSource(
        fridgeItemDao: FridgeItemDao
    ): LocaleAllItemsSource = RoomAllItemsSourceImpl(fridgeItemDao)

    @Provides
    fun provideDetailItemSource(
        fridgeItemDao: FridgeItemDao
    ): LocaleDetailItemSource = RoomDetailItemSourceImpl(fridgeItemDao)

}