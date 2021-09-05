package com.kanyideveloper.starwars.network

import com.kanyideveloper.starwars.models.Film
import com.kanyideveloper.starwars.models.People
import com.kanyideveloper.starwars.utils.Resource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("people/?page/")
    suspend fun getCharacters(@Query("page") page: Int): People

   @GET("films/")
    suspend fun getFilms(@Path("id") id: Int): Film
/*
    @GET("planets/")
    suspend fun getPlanets(@Path("id") id: Int): Resource<>

    @GET("species/")
    suspend fun getSpecies(@Path("id") id: Int): Resource<>

    @GET("starships/")
    suspend fun getStarships(@Path("id") id: Int): Resource<>

    @GET("vehicles/")
    suspend fun getVehicles(@Path("id") id: Int): Resource<>*/
}