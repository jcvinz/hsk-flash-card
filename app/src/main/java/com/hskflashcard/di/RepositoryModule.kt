package com.hskflashcard.di

import com.hskflashcard.data.repo.HSKWordRepository
import com.hskflashcard.data.repo.HSKWordRepositoryImpl
import com.hskflashcard.data.repo.UserRepository
import com.hskflashcard.data.repo.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideHSKWordRepository(hskWordRepositoryImpl: HSKWordRepositoryImpl): HSKWordRepository

}