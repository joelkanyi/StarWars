package com.kanyideveloper.starwars.di

import com.kanyideveloper.starwars.data.repositories.CharactersRepository
import com.kanyideveloper.starwars.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)

@Module
object RepositoryModule {

    @Provides
    fun providesCharactersRepository(apiService: ApiService): CharactersRepository {
        return CharactersRepository(apiService)
    }
}