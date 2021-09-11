package com.kanyideveloper.starwars.viewmodels


import androidx.lifecycle.*
import com.kanyideveloper.starwars.data.repositories.CharactersRepository
import com.kanyideveloper.starwars.models.Film
import com.kanyideveloper.starwars.models.Character
import com.kanyideveloper.starwars.models.HomeWorld
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val myArguments = savedStateHandle.get<Character>("characterDetails")
    //private val myArguments: Character? = savedStateHandle["characterDetails"]

    private val _details = MutableLiveData<Character>()

    val filmDetails: LiveData<List<Film>>
        get() = charactersRepository._filmDetails

    val homeWorld: LiveData<HomeWorld>
        get() = charactersRepository._homeWorld

    val details: LiveData<Character>
        get() = _details

    init {
        _details.value = myArguments!!
        charactersRepository.getFilmData(myArguments!!)
        charactersRepository.getHomeWorldData(myArguments.homeworld)
    }
}