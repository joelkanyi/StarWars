package com.kanyideveloper.starwars.viewmodels


import androidx.lifecycle.*
import com.kanyideveloper.starwars.data.repositories.CharactersRepository
import com.kanyideveloper.starwars.models.Film
import com.kanyideveloper.starwars.models.Character
import com.kanyideveloper.starwars.models.HomeWorld
import com.kanyideveloper.starwars.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    sealed class DetailsEvent {
        class Success(val resultData: List<Film>) : DetailsEvent()
        class Failure(val errorText: String) : DetailsEvent()
        object Loading : DetailsEvent()
        object Empty : DetailsEvent()
    }

    private val myArguments = savedStateHandle.get<Character>("characterDetails")

    private val _details = MutableLiveData<Character>()
    val details: LiveData<Character>
        get() = _details

    private val _homeWorld = MutableLiveData<DetailsEvent>(DetailsEvent.Empty)
    val homeWorld: LiveData<DetailsEvent>
        get() = _homeWorld

    private val _filmDetails = MutableLiveData<DetailsEvent>(DetailsEvent.Empty)
    val filmDetails: LiveData<DetailsEvent>
        get() = _filmDetails

    private val filmsList: ArrayList<Film> = ArrayList()

    init {
        _details.value = myArguments!!
        //getHomeWorldData(myArguments.homeworld)
        getFilmData()
    }

    private fun getFilmData() {
        myArguments!!.films.forEach { film ->

            viewModelScope.launch(Dispatchers.IO) {
                _filmDetails.value = DetailsEvent.Loading
                when (val characterDetailsResponse = charactersRepository.getFilm(film)) {
                    is Resource.Failure -> {
                        _filmDetails.value =
                            DetailsEvent.Failure(characterDetailsResponse.message!!)
                    }
                    is Resource.Success -> {
                        if (characterDetailsResponse.data == null) {
                            _filmDetails.value = DetailsEvent.Failure("Empty Film List")
                        } else {
                            filmsList.add(characterDetailsResponse.data)
                            _filmDetails.value = DetailsEvent.Success(filmsList)
                        }
                    }
                }
            }
        }
    }


    /*private fun getHomeWorldData(homeWorldUrl: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _filmDetails.value = DetailsEvent.Loading
            when (val homeWorldResponse = charactersRepository.getHomeWorld(homeWorldUrl)) {
                is Resource.Failure -> {
                    _homeWorld.value = DetailsEvent.Failure(homeWorldResponse.message!!)
                }
                is Resource.Success -> {
                    if (homeWorldResponse.data == null) {
                        _homeWorld.value = DetailsEvent.Failure("No Home World List")
                    } else {
                        _homeWorld.value = DetailsEvent.Success(homeWorldResponse.data)
                    }
                }
            }
        }
    }*/
}