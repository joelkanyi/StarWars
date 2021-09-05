package com.kanyideveloper.starwars.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kanyideveloper.starwars.data.repositories.CharactersRepository
import com.kanyideveloper.starwars.models.Result
import javax.inject.Inject

class CharacterDetailsViewModel (private val characterDetails: Result, application: Application) :
    AndroidViewModel(application) {
    private val _details = MutableLiveData<Result>()

    val details: LiveData<Result>
        get() = _details

    init {
        _details.value = characterDetails
        //charactersRepository.getFilms(characterDetails.films.)
    }

}