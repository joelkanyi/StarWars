package com.kanyideveloper.starwars.data.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kanyideveloper.starwars.models.Result
import com.kanyideveloper.starwars.network.ApiService
import com.kanyideveloper.starwars.utils.Constants.FIRST_PAGE_INDEX
import timber.log.Timber

class CharactersPagingSource(private val apiService: ApiService, private val searchString: String) :
    PagingSource<Int, Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: FIRST_PAGE_INDEX
        return try {
            val response = apiService.getCharacters(position)
            Timber.d("PagingSource: called the api")
            val characters = response.results

            val filteredData = if (searchString != null) {
                characters.filter { it.name.contains(searchString, true) }
            } else {
                characters
            }

            val nextKey = if (response.next == null) null else position + 1
            val prevKey = if (position == 1) null else position - 1

            LoadResult.Page(data = filteredData, prevKey = prevKey, nextKey = nextKey)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition
    }
}