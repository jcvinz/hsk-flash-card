package com.hskflashcard.di

import android.content.Context
import androidx.room.Room
import com.hskflashcard.data.source.local.room.AppDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideHSKWordDao(database: AppDatabase) = database.hskWordDao()

    @Provides
    fun provideLearnedHSKWordDao(database: AppDatabase) = database.learnedHSKWordDao()

    @Provides
    fun provideBookmarkedHSKWordDao(database: AppDatabase) = database.bookmarkedHSKWordDao()

}