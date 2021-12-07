package fr.adbonnin.rickandmorty.data

data class GetCharactersFilter(
    val genres: List<String> = emptyList(),
    val status: List<String> = emptyList()
)