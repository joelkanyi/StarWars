package com.kanyideveloper.starwars.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kanyideveloper.starwars.data.datasources.CharactersPagingSource
import com.kanyideveloper.starwars.models.Character
import com.kanyideveloper.starwars.network.ApiService
import com.kanyideveloper.starwars.network.SafeApiCall
import com.kanyideveloper.starwars.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService) : SafeApiCall() {

    fun getCharacters(searchString: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(apiService, searchString)
            }
        ).flow
    }

    suspend fun getFilm(url: String) = safeApiCall {
        apiService.getFilm(url)
    }

    suspend fun getHomeWorld(url: String) = safeApiCall {
        apiService.getHomeWorld(url)
    }
}