package com.kanyideveloper.starwars.data.datasources

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kanyideveloper.starwars.models.Result
import com.kanyideveloper.starwars.network.ApiService
import com.kanyideveloper.starwars.utils.Constants.FIRST_PAGE_INDEX

class CharactersPagingSource(private val apiService: ApiService) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPage : Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getCharacters()
            var nextPageNumber : Int? = null

            if (response.next != null){
                val uri = Uri.parse(response.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = nextPageNumber
            )

        }catch(e: Exception){
            LoadResult.Error(e)
        }
    }
}