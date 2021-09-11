package com.kanyideveloper.starwars.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    @SerializedName("birth_year")
    val birthYear: String,
    @SerializedName("eye_color")
    val eyeColor: String,
    @SerializedName("films")
    val films: List<String>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("hair_color")
    val hairColor: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("homeworld")
    val homeworld: String,
    @SerializedName("mass")
    val mass: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("skin_color")
    val skinColor: String
) : Parcelable