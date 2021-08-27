package com.kanyideveloper.starwars.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kanyideveloper.starwars.data.repositories.CharactersRepository
import com.kanyideveloper.starwars.models.People
import com.kanyideveloper.starwars.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    suspend fun getCharacters() = liveData {
        emit(Resource.Loading("Loading..."))
        emit(charactersRepository.getCharacters())
    }

    /*
    * val isDialogShown = dataStoreRepository.isDialogShownFlow

    private var currentResult: Flow<PagingData<PokemonResult>>? = null
    fun getPokemons(searchString: String?): Flow<PagingData<PokemonResult>> {
        val newResult: Flow<PagingData<PokemonResult>> =
            pokemonRepository.getPokemon(searchString).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }*/
}