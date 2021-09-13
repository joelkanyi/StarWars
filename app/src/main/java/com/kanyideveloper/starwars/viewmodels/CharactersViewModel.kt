package com.kanyideveloper.starwars.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kanyideveloper.starwars.data.repositories.CharactersRepository
import com.kanyideveloper.starwars.models.Character
import com.kanyideveloper.starwars.models.Film
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val charactersRepository: CharactersRepository) :
    ViewModel() {
    /*fun getCharacters(searchString: String): Flow<PagingData<Character>> {
        return charactersRepository.getCharacters(searchString).cachedIn(viewModelScope)
    }*/




    private val _characters = MutableLiveData<PagingData<Character>>()

    suspend fun getCharacters(searchString: String): LiveData<PagingData<Character>> {
        val response = charactersRepository.getCharacters(searchString).cachedIn(viewModelScope)
        _characters.value = response.value
        return response
    }
}