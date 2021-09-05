package com.kanyideveloper.starwars.data.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kanyideveloper.starwars.data.datasources.CharactersPagingSource
import com.kanyideveloper.starwars.models.Result
import com.kanyideveloper.starwars.network.ApiService
import com.kanyideveloper.starwars.network.SafeApiCall
import com.kanyideveloper.starwars.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService) : SafeApiCall() {

    suspend fun getFilms(id: Int) = safeApiCall {
        apiService.getFilms(id)
    }

    fun getCharacters(searchString: String): LiveData<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(apiService, searchString)
            }
        ).liveData
    }
}