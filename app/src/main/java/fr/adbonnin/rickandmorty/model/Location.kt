package fr.adbonnin.rickandmorty.model

data class Location(
    val id: Int?,
    val name: String?,
    val type: String?,
    val dimension: String?,
    val residents: List<Character>?,
    val created: String?,
)