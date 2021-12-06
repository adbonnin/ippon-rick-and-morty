package fr.adbonnin.rickandmorty.model

import com.google.gson.annotations.SerializedName

data class Episode(
    val id: Int?,
    val name: String?,
    @SerializedName("air_date") val airDate: String?,
    val episode: String?,
    val characters: List<String>?,
    val created: String?,
)