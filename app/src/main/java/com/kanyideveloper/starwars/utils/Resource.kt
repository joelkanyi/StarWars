package com.kanyideveloper.starwars.utils

import com.kanyideveloper.starwars.viewmodels.CharacterDetailsViewModel

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
   class Success<T>(data: T) : Resource<T>(data)
   class Loading<T>(data: T? = null) : Resource<T>(data)
   class Failure<T>(message: String, data: T? = null) : Resource<T>(data, message)
}