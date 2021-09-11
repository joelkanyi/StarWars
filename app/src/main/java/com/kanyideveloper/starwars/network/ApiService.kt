package com.kanyideveloper.starwars.network

import com.kanyideveloper.starwars.models.Film
import com.kanyideveloper.starwars.models.HomeWorld
import com.kanyideveloper.starwars.models.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("people/?page/")
    suspend fun getCharacters(@Query("page") page: Int): PeopleResponse

    @GET
    suspend fun getFilm(@Url url: String): Film

    @GET
    suspend fun getHomeWorld(@Url url: String): HomeWorld
}