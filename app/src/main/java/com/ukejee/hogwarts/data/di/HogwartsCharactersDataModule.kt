package com.ukejee.hogwarts.data.di

import com.ukejee.hogwarts.data.cache.AppDatabase
import com.ukejee.hogwarts.data.datasource.HogwartsLocalDataSource
import com.ukejee.hogwarts.data.datasource.HogwartsRemoteDataSource
import com.ukejee.hogwarts.data.datasource.HogwartsRetrofitDataSource
import com.ukejee.hogwarts.data.datasource.HogwartsRoomDataSource
import com.ukejee.hogwarts.data.network.endpoint.HogwartsCharactersEndpoint
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HogwartsCharactersDataModule {

    @Provides
    @Singleton
    fun provideHogwartsCharactersEndpoint(retrofit: Retrofit): HogwartsCharactersEndpoint {
        return retrofit.create(HogwartsCharactersEndpoint::class.java)
    }

    @Provides
    @Singleton
    fun provideHogwartsRemoteDataSource(hogwartsCharactersEndpoint: HogwartsCharactersEndpoint): HogwartsRemoteDataSource {
        return HogwartsRetrofitDataSource(hogwartsCharactersEndpoint)
    }

    @Provides
    @Singleton
    fun provideHogwartsLocalDataSource(appDatabase: AppDatabase): HogwartsLocalDataSource {
        return HogwartsRoomDataSource(appDatabase)
    }
}