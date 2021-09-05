package com.kanyideveloper.starwars.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kanyideveloper.starwars.models.Result

class CharacterDetailsViewModelFactory(
    private val characterDetails: Result,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            return CharacterDetailsViewModel(characterDetails, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}