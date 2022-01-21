package com.example.paginationpractice.di

import android.content.Context
import androidx.room.Room
import com.example.paginationpractice.db.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "News"
    ).build()

    @Singleton
    @Provides
    fun provideNewsDao(database: NewsDatabase) = database.newsDao()

    @Singleton
    @Provides
    fun provideRemoteDao(database: NewsDatabase) = database.remoteDao()

}