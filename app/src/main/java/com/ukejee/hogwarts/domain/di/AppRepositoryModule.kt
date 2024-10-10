package com.ukejee.hogwarts.domain.di

import com.ukejee.hogwarts.data.datasource.HogwartsLocalDataSource
import com.ukejee.hogwarts.data.datasource.HogwartsRemoteDataSource
import com.ukejee.hogwarts.domain.repository.AppRepository
import com.ukejee.hogwarts.domain.repository.AppRepositoryContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppRepositoryModule {

    @Provides
    @Singleton
    fun providesAppRepository(
        hogwartsLocalDataSource: HogwartsLocalDataSource,
        hogwartsRemoteDataSource: HogwartsRemoteDataSource
    ): AppRepositoryContract {
        return AppRepository(hogwartsLocalDataSource, hogwartsRemoteDataSource)
    }
}