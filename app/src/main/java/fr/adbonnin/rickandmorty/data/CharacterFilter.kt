package fr.adbonnin.rickandmorty.data

import fr.adbonnin.rickandmorty.model.Character

data class CharacterFilter(
    val genres: List<Character.Gender> = emptyList(),
    val status: List<Character.Status> = emptyList()
)