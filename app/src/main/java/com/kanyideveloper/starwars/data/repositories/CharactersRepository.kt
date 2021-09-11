package com.kanyideveloper.starwars.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.kanyideveloper.starwars.data.datasources.CharactersPagingSource
import com.kanyideveloper.starwars.models.Film
import com.kanyideveloper.starwars.models.Character
import com.kanyideveloper.starwars.models.HomeWorld
import com.kanyideveloper.starwars.network.ApiService
import com.kanyideveloper.starwars.network.SafeApiCall
import com.kanyideveloper.starwars.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService) : SafeApiCall() {

    val _filmDetails = MutableLiveData<List<Film>>()
    val _homeWorld = MutableLiveData<HomeWorld>()
    private val filmsList: ArrayList<Film> = ArrayList()

    fun getCharacters(searchString: String): LiveData<PagingData<Character>> {
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

    private suspend fun getFilm(url: String) = safeApiCall {
        apiService.getFilm(url)
    }

    private suspend fun getHomeWorld(url: String) = safeApiCall {
        apiService.getHomeWorld(url)
    }

    fun getFilmData(characterDetails: Character) {
        characterDetails.films.forEach { film ->
            CoroutineScope(Dispatchers.Main).launch {
                filmsList.add(getFilm(film).data!!)
                _filmDetails.value = filmsList
                Timber.d(filmsList.toString())
            }
        }
    }

    fun getHomeWorldData(homeWorldUrl: String){
        CoroutineScope(Dispatchers.Main).launch {
            _homeWorld.value = getHomeWorld(homeWorldUrl).data!!
            Timber.d(filmsList.toString())
        }
    }
}