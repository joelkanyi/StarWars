package com.kanyideveloper.starwars.models


import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("opening_crawl")
    val openingCrawl: String,
    @SerializedName("title")
    val title: String
)