package com.kanyideveloper.starwars.models


import com.google.gson.annotations.SerializedName

data class HomeWorld(
    @SerializedName("name")
    val name: String
)