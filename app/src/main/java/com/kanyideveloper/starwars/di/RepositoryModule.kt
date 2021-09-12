package com.kanyideveloper.starwars.di

import com.kanyideveloper.starwars.data.repositories.CharactersRepository
import com.kanyideveloper.starwars.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object RepositoryModule {

    @Singleton
    @Provides
    fun providesCharactersRepository(apiService: ApiService): CharactersRepository {
        return CharactersRepository(apiService)
    }
}