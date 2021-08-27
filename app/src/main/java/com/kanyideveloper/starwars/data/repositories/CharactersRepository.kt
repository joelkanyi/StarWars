package com.kanyideveloper.starwars.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kanyideveloper.starwars.data.datasources.CharactersPagingSource
import com.kanyideveloper.starwars.models.People
import com.kanyideveloper.starwars.models.Result
import com.kanyideveloper.starwars.network.ApiService
import com.kanyideveloper.starwars.network.SafeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService) : SafeApiCall() {

    /*suspend fun getCharacters() = safeApiCall {
        apiService.getCharacters()
    }*/

//    fun getCharacters() = Pager(
//        config = PagingConfig(enablePlaceholders = false, pageSize = 27),
//        pagingSourceFactory = {
//            CharactersPagingSource(apiService)
//        }
//    ).liveData

    fun getCharacters() : Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 30),
            pagingSourceFactory = {
                CharactersPagingSource(apiService)
            }
        ).flow
    }
}